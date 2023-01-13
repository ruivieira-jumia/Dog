package com.android.core_model


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DogBreed(
    val breedGroup: String? = null,
    val id: Int? = null,
    val imageUrl: String? = null,
    val name: String? = null,
    val origin: String? = null,
    val temperament: String? = null
) : Parcelable