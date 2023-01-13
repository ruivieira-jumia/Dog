package com.android.core_network.di

import com.android.core_network.breed.DogBreedDataSource
import com.android.core_network.breed.DogBreedDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class NetworkDataSourceModule {

    @Binds
    abstract fun bindDogBreedDataSource(dogBreedDataSourceImpl: DogBreedDataSourceImpl): DogBreedDataSource
}