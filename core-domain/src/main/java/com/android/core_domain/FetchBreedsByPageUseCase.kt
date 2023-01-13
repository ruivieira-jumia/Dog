package com.android.core_domain

import com.android.core_data.repository.DogBreedRepository
import javax.inject.Inject

class FetchBreedsByPageUseCase @Inject constructor(private val dogBreedRepository: DogBreedRepository) {
    operator fun invoke(page: Int) = dogBreedRepository.getDogBreedsByPage(page)
}