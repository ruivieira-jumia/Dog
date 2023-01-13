package com.android.core_network.breed

import com.android.core_model.Resource
import com.android.core_network.model.BreedItem

interface DogBreedDataSource {

    suspend fun getDogBreedsByPage(page: Int): Resource<List<BreedItem>>

    suspend fun getDogBreedsBySearchQuery(searchQuery: String): Resource<List<BreedItem>>

}