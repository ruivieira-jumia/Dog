package com.android.core_data.di

import com.android.core_data.repository.DogBreedRepository
import com.android.core_data.repository.DogBreedRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindDogBreedRepository(dogBreedRepository: DogBreedRepositoryImpl): DogBreedRepository
}