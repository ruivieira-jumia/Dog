package com.android.navigation

import androidx.navigation.NavController

class Navigator {
    lateinit var navController: NavController

    fun navigateToFlow(navigationFlow: NavigationFlow) {
        try {
            when (navigationFlow) {
                is NavigationFlow.DogsListToDogBreedDetailsFlow -> navController.navigate(NavGraphDirections.actionDogsListToDogDetailsFlow(navigationFlow.dogBreed))
                is NavigationFlow.SearchDogToDogBreedDetailsFlow -> navController.navigate(NavGraphDirections.actionSearchDogToDogDetailsFlow(navigationFlow.dogBreed))
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }
}