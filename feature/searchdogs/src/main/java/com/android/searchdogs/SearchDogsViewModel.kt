package com.android.searchdogs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.core_domain.FetchBreedsBySearchUseCase
import com.android.core_model.Resource
import com.android.core_model.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchDogsViewModel @Inject constructor(
    private val fetchBreedsBySearchUseCase: FetchBreedsBySearchUseCase
) : ViewModel(), SearchDogsViewModelContract.ViewModel {

    override fun invokeAction(action: SearchDogsViewModelContract.Action) {
        when (action) {
            is SearchDogsViewModelContract.Action.FetchBreedsBySearchQuery -> searchBreeds(action.searchQuery)
            is SearchDogsViewModelContract.Action.NavigateToDogDetails -> _uiEventData.postValue(SearchDogsViewModelContract.Event.NavigateToBreedDetailsView(action.dogBreed))
        }
    }

    private val _uiStateData = MediatorLiveData<SearchDogsViewModelContract.State>()
    override val uiStateData: LiveData<SearchDogsViewModelContract.State> = _uiStateData

    private val _uiEventData = SingleLiveEvent<SearchDogsViewModelContract.Event>()
    override val uiEventData: LiveData<SearchDogsViewModelContract.Event> = _uiEventData


    private fun searchBreeds(searchQuery: String) {
        viewModelScope.launch(Dispatchers.IO) {
            fetchBreedsBySearchUseCase.invoke(searchQuery).collect { resource ->
                when (resource) {
                    is Resource.Error ->
                        resource.message?.let {
                            _uiStateData.postValue(SearchDogsViewModelContract.State.ErrorState(it))
                        }
                    is Resource.Loading -> _uiStateData.postValue(SearchDogsViewModelContract.State.LoadingState)
                    is Resource.Success ->
                        resource.data?.let {
                            _uiStateData.postValue(SearchDogsViewModelContract.State.SuccessState(it))
                        }
                }
            }
        }
    }
}