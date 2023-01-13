package com.android.core_domain

import com.android.core_data.repository.DogBreedRepository
import io.mockk.clearAllMocks
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.junit.Test

class FetchBreedsByPageUseCaseTest {

    private lateinit var fetchBreedsByPageUseCase: FetchBreedsByPageUseCase

    private val dogBreedRepository = mockk<DogBreedRepository>(relaxed = true)

    @Before
    fun setup() {
        fetchBreedsByPageUseCase = FetchBreedsByPageUseCase(dogBreedRepository)
    }

    @Test
    fun `invoke should call repository and get data by page`() {
        val page = 1
        fetchBreedsByPageUseCase.invoke(page)
        coVerify { dogBreedRepository.getDogBreedsByPage(page) }
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }
}