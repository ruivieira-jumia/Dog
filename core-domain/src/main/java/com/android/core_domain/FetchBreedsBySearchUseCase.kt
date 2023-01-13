package com.android.core_domain

import com.android.core_data.repository.DogBreedRepository
import javax.inject.Inject

class FetchBreedsBySearchUseCase @Inject constructor(private val dogBreedRepository: DogBreedRepository) {
    operator fun invoke(searchQuery: String) = dogBreedRepository.getDogBreedsBySearchQuery(searchQuery)
}