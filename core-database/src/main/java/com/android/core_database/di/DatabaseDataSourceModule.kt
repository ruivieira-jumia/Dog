package com.android.core_database.di

import com.android.core_database.datasource.BreedLocalDataSource
import com.android.core_database.datasource.BreedLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DatabaseDataSourceModule {

    @Binds
    abstract fun bindBreedLocalDataSource(breedLocalDataSourceImpl: BreedLocalDataSourceImpl): BreedLocalDataSource
}