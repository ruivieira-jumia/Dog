package com.android.searchdogs

import androidx.lifecycle.LiveData
import com.android.core_model.DogBreed

sealed class SearchDogsViewModelContract {

    interface ViewModel {
        val uiStateData: LiveData<State>
        fun invokeAction(action: Action)
        val uiEventData: LiveData<Event>
    }


    sealed class Action {
        data class NavigateToDogDetails(val dogBreed: DogBreed) : Action()
        data class FetchBreedsBySearchQuery(val searchQuery : String) : Action()
    }

    sealed class State {
        object LoadingState : State()
        data class SuccessState(val breedList: List<DogBreed>) : State()
        data class ErrorState(val message : String) : State()
    }

    sealed class Event {
        data class NavigateToBreedDetailsView(val dogBreed: DogBreed) : Event()
    }
}