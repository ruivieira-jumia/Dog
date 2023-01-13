package com.android.dogbreeds

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.core_domain.FetchBreedsByPageUseCase
import com.android.core_model.DogBreed
import com.android.core_model.Resource
import com.android.core_model.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogsListViewModel @Inject constructor(
    private val fetchBreedsByPageUseCase: FetchBreedsByPageUseCase
) : ViewModel(), DogsListViewModelContract.ViewModel {

    private val _uiStateData = MediatorLiveData<DogsListViewModelContract.State>()
    override val uiStateData: LiveData<DogsListViewModelContract.State> = _uiStateData

    private val _uiEventData = SingleLiveEvent<DogsListViewModelContract.Event>()
    override val uiEventData: LiveData<DogsListViewModelContract.Event> = _uiEventData

    override fun invokeAction(action: DogsListViewModelContract.Action) {
        when (action) {
            is DogsListViewModelContract.Action.InitPage -> initPage()
            is DogsListViewModelContract.Action.NavigateToDogDetails -> _uiEventData.postValue(DogsListViewModelContract.Event.NavigateToBreedDetailsView(action.dogBreed))
            is DogsListViewModelContract.Action.FetchBreedsByPage -> fetchBreeds(action.lastItem)
            is DogsListViewModelContract.Action.ChangeView -> changeView()
            is DogsListViewModelContract.Action.SortAlphabetically -> sortAlphabetically()
        }
    }

    private var currentItems = 0
    private var page = 0
    private var isGrid = false
    private var sortedAlphabetically = true
    private var currentList = mutableListOf<DogBreed>()

    private fun initPage() {
        currentList = mutableListOf()
        currentItems = 0
        sortedAlphabetically = true
        isGrid = false
        page = 0
        fetchBreeds()
    }

    private fun fetchBreeds(lastVisibleItem: Int = 0) {
        viewModelScope.launch(Dispatchers.IO) {
            if (lastVisibleItem >= currentItems.minus(4)) {
                fetchBreedsByPageUseCase.invoke(page).collect { resource ->
                    when (resource) {
                        is Resource.Loading -> _uiStateData.postValue(DogsListViewModelContract.State.LoadingState)
                        is Resource.Success -> resource.data?.let { handleSuccessState(it) }
                        is Resource.Error -> {
                            resource.message?.let {
                                _uiStateData.postValue(DogsListViewModelContract.State.ErrorState(it))
                            }
                        }
                    }
                }
            }
        }
    }

    private fun handleSuccessState(list: List<DogBreed>) {
        currentList = (currentList + list).toMutableList()
        val sortedList = if (sortedAlphabetically) currentList else currentList.reversed()
        _uiStateData.postValue(DogsListViewModelContract.State.SuccessState(sortedList))
        page++
        currentItems = sortedList.size
    }

    private fun changeView() {
        isGrid = !isGrid
        _uiEventData.postValue(DogsListViewModelContract.Event.ChangeView(isGrid))
    }

    private fun sortAlphabetically() {
        sortedAlphabetically = !sortedAlphabetically
        _uiEventData.postValue(DogsListViewModelContract.Event.SortAlphabetically(sortedAlphabetically))
    }

}