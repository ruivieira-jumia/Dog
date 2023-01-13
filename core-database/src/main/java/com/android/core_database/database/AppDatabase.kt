package com.android.core_database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.core_database.model.BreedEntity

@Database(
    entities = [BreedEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dogBreedDao(): DogBreedDao
}