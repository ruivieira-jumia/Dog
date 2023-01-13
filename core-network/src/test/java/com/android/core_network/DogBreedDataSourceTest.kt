package com.android.core_network

import com.android.core_network.NetworkConstants.PAGE_LIMIT
import com.android.core_network.api.NetworkApi
import com.android.core_network.breed.DogBreedDataSourceImpl
import io.mockk.clearAllMocks
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DogBreedDataSourceTest {

    private lateinit var dataSourceImpl: DogBreedDataSourceImpl

    private val networkApi = mockk<NetworkApi>(relaxed = true)

    @Before
    fun setup() {
        dataSourceImpl = DogBreedDataSourceImpl((networkApi))
    }

    @Test
    fun `datasource getDogBreedsByPage should call apiInterface fetchDogBreedsByPage`() = runTest {
        val page = 1
        dataSourceImpl.getDogBreedsByPage(page)
        coVerify { networkApi.fetchDogBreedsByPage(PAGE_LIMIT, page) }
    }

    @Test
    fun `datasource getDogBreedsBySearchQuery should call networkApi fetchDogBreedsBySearchQuery`() = runTest {
        val query = "query"
        dataSourceImpl.getDogBreedsBySearchQuery(query)
        coVerify { networkApi.fetchDogBreedsBySearchQuery(query) }
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }
}