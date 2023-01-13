package com.android.core_data

import com.android.core_data.repository.DogBreedRepositoryImpl
import com.android.core_database.datasource.BreedLocalDataSource
import com.android.core_database.model.BreedEntity
import com.android.core_model.DogBreed
import com.android.core_model.Resource
import com.android.core_network.breed.DogBreedDataSource
import com.android.core_network.model.BreedItem
import com.android.core_network.model.Image
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class DogBreedRepositoryTest {

    private lateinit var dogBreedRepositoryImpl: DogBreedRepositoryImpl

    private val dogBreedDataSource = mockk<DogBreedDataSource>(relaxed = true)
    private val localDataSource = mockk<BreedLocalDataSource>(relaxed = true)

    @Before
    fun setup() {
        dogBreedRepositoryImpl = DogBreedRepositoryImpl(dogBreedDataSource, localDataSource)
    }

    @Test
    fun `when repository getDogBreedsByPage is called and result is error `() = runTest {
        val page = 1
        coEvery { dogBreedDataSource.getDogBreedsByPage(page) } returns Resource.Error("Error")
        coEvery { localDataSource.queryBreeds(page) } returns listOf()

        dogBreedRepositoryImpl.getDogBreedsByPage(page).collect()

        coVerify { dogBreedDataSource.getDogBreedsByPage(page) }
        coVerify { localDataSource.queryBreeds(page) }
    }

    @Test
    fun `when repository getDogBreedsByPage is called and result is success `() = runTest {
        val page = 1
        coEvery { dogBreedDataSource.getDogBreedsByPage(page) } returns Resource.Success(listOf())
        coEvery { localDataSource.insertBreeds(any()) } returns Unit
        coEvery { localDataSource.queryBreeds(page) } returns listOf()

        dogBreedRepositoryImpl.getDogBreedsByPage(page).collect()

        coVerify { dogBreedDataSource.getDogBreedsByPage(page) }
        coVerify { localDataSource.insertBreeds(any()) }
        coVerify { localDataSource.queryBreeds(page) }
    }

    @Test
    fun `when repository getDogBreedsBySearchQuery should call getDogBreedsBySearchQuery and is Success`() = runTest {
        val query = "query"
        coEvery { dogBreedDataSource.getDogBreedsBySearchQuery(query) } returns Resource.Success(listOf())

        dogBreedRepositoryImpl.getDogBreedsBySearchQuery(query).collect()
        coVerify { dogBreedDataSource.getDogBreedsBySearchQuery(query) }
    }

    @Test
    fun `when repository getDogBreedsBySearchQuery should call getDogBreedsBySearchQuery and is emptyList return Error`() = runTest {
        val query = "query"
        coEvery { dogBreedDataSource.getDogBreedsBySearchQuery(query) } returns Resource.Success(emptyList())

        dogBreedRepositoryImpl.getDogBreedsBySearchQuery(query).collect()
        coVerify { dogBreedDataSource.getDogBreedsBySearchQuery(query) }
    }

    @Test
    fun `when repository getDogBreedsBySearchQuery should call getDogBreedsBySearchQuery and is Error`() = runTest {
        val query = "query"
        coEvery { dogBreedDataSource.getDogBreedsBySearchQuery(query) } returns Resource.Error("Error")

        dogBreedRepositoryImpl.getDogBreedsBySearchQuery(query).collect()
        coVerify { dogBreedDataSource.getDogBreedsBySearchQuery(query) }
    }
}