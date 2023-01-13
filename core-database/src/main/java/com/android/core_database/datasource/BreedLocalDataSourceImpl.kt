package com.android.core_database.datasource

import com.android.core_database.database.DogBreedDao
import com.android.core_database.model.BreedEntity
import javax.inject.Inject

class BreedLocalDataSourceImpl @Inject constructor(private val dao: DogBreedDao) : BreedLocalDataSource {
    override suspend fun insertBreeds(breeds: List<BreedEntity>) {
        dao.insertBreeds(breeds)
    }

    override suspend fun queryBreeds(page : Int): List<BreedEntity> = dao.queryBreeds(page)
}