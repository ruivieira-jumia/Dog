package com.android.dogbreeds

import androidx.lifecycle.LiveData
import com.android.core_model.DogBreed

sealed class DogsListViewModelContract {

    interface ViewModel {
        val uiStateData: LiveData<State>
        fun invokeAction(action: Action)
        val uiEventData: LiveData<Event>
    }


    sealed class Action {
        data class NavigateToDogDetails(val dogBreed: DogBreed) : Action()
        data class FetchBreedsByPage(val lastItem: Int) : Action()

        object InitPage : Action()
        object ChangeView : Action()
        object SortAlphabetically : Action()
    }

    sealed class State {
        object LoadingState : State()
        data class SuccessState(val breedList: List<DogBreed>) : State()
        data class ErrorState(val message: String) : State()
    }

    sealed class Event {
        data class NavigateToBreedDetailsView(val dogBreed: DogBreed) : Event()
        data class ChangeView(val isGrid: Boolean) : Event()
        data class SortAlphabetically(val sortedAlphabetically: Boolean) : Event()
    }
}