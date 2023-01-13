package com.android.core_network.api

import com.android.core_network.model.BreedItem
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkApi {

    @GET("breeds")
    suspend fun fetchDogBreedsByPage(
        @Query("limit") pageLimit: Int, @Query("page") pageNumber: Int
    ): List<BreedItem>

    @GET("breeds/search")
    suspend fun fetchDogBreedsBySearchQuery(@Query("q") searchQuery: String): List<BreedItem>
}