package com.android.core_data.repository

import com.android.core_database.datasource.BreedLocalDataSource
import com.android.core_database.model.asEntity
import com.android.core_database.model.asExternalModel
import com.android.core_model.DogBreed
import com.android.core_model.Resource
import com.android.core_network.breed.DogBreedDataSource
import com.android.core_network.model.asExternalModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DogBreedRepositoryImpl @Inject constructor(private val dogBreedDataSource: DogBreedDataSource, private val localDataSource: BreedLocalDataSource) : DogBreedRepository {
    override fun getDogBreedsByPage(page: Int): Flow<Resource<List<DogBreed>>> = flow {
        emit(Resource.Loading())

        val remoteResponse = dogBreedDataSource.getDogBreedsByPage(page)

        if (remoteResponse is Resource.Success)
            remoteResponse.data?.map { breedItem -> breedItem.asExternalModel().asEntity() }?.let { breedEntityList ->
                breedEntityList.map { it.page = page }
                localDataSource.insertBreeds(breedEntityList)
            }

        val localResponse = localDataSource.queryBreeds(page).map { it.asExternalModel() }

        if (localResponse.isNotEmpty())
            emit(Resource.Success(localResponse))
        else
            emit(Resource.Error("There's no data"))

    }

    override fun getDogBreedsBySearchQuery(searchQuery: String): Flow<Resource<List<DogBreed>>> = flow {
        emit(Resource.Loading())

        val remoteResponse = dogBreedDataSource.getDogBreedsBySearchQuery(searchQuery)
        if (remoteResponse is Resource.Success)
            remoteResponse.data?.map { breedItem -> breedItem.asExternalModel() }?.let {
                if (it.isNotEmpty())
                    emit(Resource.Success(it))
                else
                    emit(Resource.Error("There's no data"))
            }
        else emit(Resource.Error(remoteResponse.message))
    }
}