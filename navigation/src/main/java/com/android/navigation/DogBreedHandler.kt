package com.android.navigation

import com.android.core_model.DogBreed

//If I had more common ui stuff I would create a module for the common ui and put this interface in there, but since I just have this
//interface being shared i decided to put in the navigation module
interface DogBreedHandler {
    fun onDogClick(dogBreed: DogBreed)
}