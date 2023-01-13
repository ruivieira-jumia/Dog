package com.android.core_database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.core_model.DogBreed

@Entity(tableName = "Breed")
data class BreedEntity(
    @PrimaryKey
    val id: Int? = null,
    @ColumnInfo(name = "breed_group")
    val breedGroup: String? = null,
    val imageUrl: String? = null,
    val name: String? = null,
    val origin: String? = null,
    val temperament: String? = null,
    var page: Int? = null
)

fun DogBreed.asEntity() = BreedEntity(
    id = id,
    breedGroup = breedGroup,
    imageUrl = imageUrl,
    name = name,
    origin = origin,
    temperament = temperament
)

fun BreedEntity.asExternalModel() = DogBreed(
    id = id,
    breedGroup = breedGroup,
    imageUrl = imageUrl,
    name = name,
    origin = origin,
    temperament = temperament
)