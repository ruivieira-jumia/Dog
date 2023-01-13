package com.android.core_network.breed

import com.android.core_model.Resource
import com.android.core_network.NetworkConstants.PAGE_LIMIT
import com.android.core_network.api.NetworkApi
import com.android.core_network.model.BreedItem
import com.android.core_network.requestData
import javax.inject.Inject

class DogBreedDataSourceImpl @Inject constructor(
    private val networkApi: NetworkApi
) : DogBreedDataSource {
    override suspend fun getDogBreedsByPage(page: Int): Resource<List<BreedItem>> = requestData {
        networkApi.fetchDogBreedsByPage(PAGE_LIMIT, page)
    }

    override suspend fun getDogBreedsBySearchQuery(searchQuery: String): Resource<List<BreedItem>> = requestData {
        networkApi.fetchDogBreedsBySearchQuery(searchQuery)
    }
}