package com.android.core_data.repository

import com.android.core_model.DogBreed
import com.android.core_model.Resource
import kotlinx.coroutines.flow.Flow


interface DogBreedRepository {
    fun getDogBreedsByPage(page: Int): Flow<Resource<List<DogBreed>>>
    fun getDogBreedsBySearchQuery(searchQuery: String): Flow<Resource<List<DogBreed>>>
}