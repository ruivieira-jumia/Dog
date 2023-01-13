package com.android.dogbreeds

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.android.core_domain.FetchBreedsByPageUseCase
import com.android.core_model.DogBreed
import com.android.dogbreeds.LiveDataUtil.getOrAwaitValue
import io.mockk.clearAllMocks
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.*

class DogsListViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: DogsListViewModel

    private val fetchBreedsByPageUseCase = mockk<FetchBreedsByPageUseCase>(relaxed = true)

    @Before
    fun setup() {
        viewModel = DogsListViewModel(fetchBreedsByPageUseCase)

        viewModel.uiEventData.observeForever {}
    }

    @Test
    fun `when FetchBreedsByPage and fetchBreedsByPageUseCase is called `() = runTest {
        viewModel.invokeAction(DogsListViewModelContract.Action.FetchBreedsByPage(0))
        coVerify { fetchBreedsByPageUseCase.invoke(any()) }
    }

    @Test
    fun `when SortAlphabetically action is called trigger SortAlphabetically`() {
        viewModel.invokeAction(DogsListViewModelContract.Action.SortAlphabetically)
        val actual = viewModel.uiEventData.getOrAwaitValue()

        val expected = DogsListViewModelContract.Event.SortAlphabetically(false)
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `when ChangeView action is called trigger ChangeView`() {
        viewModel.invokeAction(DogsListViewModelContract.Action.ChangeView)
        val actual = viewModel.uiEventData.getOrAwaitValue()

        val expected = DogsListViewModelContract.Event.ChangeView(true)
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `when NavigateToDogDetails action is called trigger NavigateToBreedDetailsView`() = runTest {
        val breed = DogBreed()
        viewModel.invokeAction(DogsListViewModelContract.Action.NavigateToDogDetails(breed))
        val actual = viewModel.uiEventData.getOrAwaitValue()

        val expected = DogsListViewModelContract.Event.NavigateToBreedDetailsView(breed)
        Assert.assertEquals(expected, actual)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }
}