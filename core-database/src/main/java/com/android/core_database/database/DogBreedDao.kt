package com.android.core_database.database

import androidx.room.*
import com.android.core_database.model.BreedEntity

@Dao
interface DogBreedDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBreeds(breeds: List<BreedEntity>)

    @Transaction
    @Query("SELECT * FROM Breed WHERE page = :page")
    suspend fun queryBreeds(page: Int): List<BreedEntity>
}