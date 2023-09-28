package com.plcoding.composepaging3caching.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.plcoding.composepaging3caching.data.database.BeerDatabase
import com.plcoding.composepaging3caching.data.database.BeerEntity
import com.plcoding.composepaging3caching.data.mapper.toBeerEntity
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class BeerRemoteMediator(
    private val beerDb: BeerDatabase,
    private val beerAPI: BeerAPI
) : RemoteMediator<Int, BeerEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, BeerEntity>
    ): MediatorResult {

        return try {

            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1

                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )


                LoadType.APPEND -> {

                    val lastItem = state.lastItemOrNull()

                    if (lastItem == null) {
                        1
                    } else {
                        // item.id is in order
                        // if not you have to change this logic to get last item count
                        (lastItem.id / state.config.pageSize) + 1
                    }
                }
            }
            //delay to verify the loading
            delay(3000)
            val beers = beerAPI.getBears(
                page = loadKey,
                pageCount = state.config.pageSize
            )

            beerDb.withTransaction {

                if (loadType == LoadType.REFRESH) {
                    beerDb.beerDao.clearAll()
                }

                val beerEntities = beers.map { it.toBeerEntity() }
                beerDb.beerDao.upsertAll(beerEntities)

            }

            MediatorResult.Success(endOfPaginationReached = beers.isEmpty())

        } catch (e: HttpException) {
            MediatorResult.Error(e)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        }
    }
}