package com.plcoding.composepaging3caching.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.plcoding.composepaging3caching.data.database.BeerDatabase
import com.plcoding.composepaging3caching.data.database.BeerEntity
import com.plcoding.composepaging3caching.data.remote.BeerAPI
import com.plcoding.composepaging3caching.data.remote.BeerRemoteMediator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBeerDatabase(@ApplicationContext context: Context): BeerDatabase {
        return Room.databaseBuilder(
            context,
            BeerDatabase::class.java, "beers.db"
        ).build()
    }


    @Provides
    @Singleton
    fun provideBeerAPI(): BeerAPI {
        return Retrofit.Builder().baseUrl(BeerAPI.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create()).build().create()
    }

    @Provides
    @Singleton
    fun provideBeerPager(beerDatabase: BeerDatabase, beerAPI: BeerAPI): Pager<Int, BeerEntity> {

        return Pager(
            config = PagingConfig(20),
            remoteMediator = BeerRemoteMediator(beerDb = beerDatabase, beerAPI = beerAPI),
            pagingSourceFactory = { beerDatabase.beerDao.pagingSource() }
        )
    }


}