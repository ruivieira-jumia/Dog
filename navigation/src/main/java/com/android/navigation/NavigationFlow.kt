package com.android.navigation

import com.android.core_model.DogBreed

sealed class NavigationFlow {
    class DogsListToDogBreedDetailsFlow(val dogBreed: DogBreed) : NavigationFlow()
    class SearchDogToDogBreedDetailsFlow(val dogBreed: DogBreed) : NavigationFlow()
}