package com.android.core_database.datasource

import com.android.core_database.model.BreedEntity

interface BreedLocalDataSource {
    suspend fun insertBreeds(breeds: List<BreedEntity>)

    suspend fun queryBreeds(page : Int): List<BreedEntity>
}