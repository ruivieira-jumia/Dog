package com.android.core_network.model


import com.android.core_model.DogBreed
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BreedItem(
    @SerializedName("breed_group")
    @Expose
    val breedGroup: String?,
    @Expose
    val id: Int?,
    @SerializedName("image")
    @Expose
    val image: Image?,
    @SerializedName("name")
    @Expose
    val name: String?,
    @SerializedName("origin")
    @Expose
    val origin: String?,
    @SerializedName("temperament")
    @Expose
    val temperament: String?
)

fun BreedItem.asExternalModel() = DogBreed(
    id = id,
    breedGroup = breedGroup,
    imageUrl = image?.url ?: "",
    name = name,
    origin = origin,
    temperament = temperament
)