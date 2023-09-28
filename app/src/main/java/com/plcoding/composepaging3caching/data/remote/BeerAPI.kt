package com.plcoding.composepaging3caching.data.remote

import com.plcoding.composepaging3caching.data.BeerDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface BeerAPI {

    @GET("beers")
    suspend fun getBears(
        @Query("page") page:Int,
        @Query("per_page") pageCount:Int
    ): List<BeerDTO>


    companion object{
        const val BASE_URL = "https://api.punkapi.com/v2/"
    }
}