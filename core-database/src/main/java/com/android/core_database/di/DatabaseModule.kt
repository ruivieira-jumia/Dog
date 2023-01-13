package com.android.core_database.di

import android.app.Application
import androidx.room.Room
import com.android.core_database.database.AppDatabase
import com.android.core_database.DatabaseConstants.DATABASE_NAME
import com.android.core_database.database.DogBreedDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideDogBreedDao(db: AppDatabase): DogBreedDao {
        return db.dogBreedDao()
    }
}