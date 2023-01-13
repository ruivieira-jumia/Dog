package com.android.core_domain

import com.android.core_data.repository.DogBreedRepository
import io.mockk.clearAllMocks
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.junit.Test

class FetchBreedsBySearchUseCaseTest {

    private lateinit var fetchBreedsBySearchUseCase: FetchBreedsBySearchUseCase

    private val dogBreedRepository = mockk<DogBreedRepository>(relaxed = true)

    @Before
    fun setup() {
        fetchBreedsBySearchUseCase = FetchBreedsBySearchUseCase(dogBreedRepository)
    }

    @Test
    fun `invoke should call repository and get data by page`() {
        val searchQuery = "search"
        fetchBreedsBySearchUseCase.invoke(searchQuery)
        coVerify { dogBreedRepository.getDogBreedsBySearchQuery(searchQuery) }
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }
}