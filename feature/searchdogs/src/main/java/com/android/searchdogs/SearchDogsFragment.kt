package com.android.searchdogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.core_model.DogBreed
import com.android.navigation.DogBreedHandler
import com.android.navigation.NavigateToFlow
import com.android.navigation.NavigationFlow
import com.android.searchdogs.databinding.FragmentSearchDogsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchDogsFragment : Fragment(), DogBreedHandler {

    private val viewModel: SearchDogsViewModel by viewModels()

    private lateinit var binding: FragmentSearchDogsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchDogsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.uiStateData.observe(viewLifecycleOwner, Observer(::performViewState))
        viewModel.uiEventData.observe(viewLifecycleOwner, Observer(::performViewEvent))

        configureRecyclerView()
        configureSearchView()
    }

    private fun configureSearchView() {
        binding.svDogs.onActionViewExpanded()
        binding.svDogs.setOnQueryTextListener(object : OnQueryTextListener, SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty())
                    (binding.rvSearchDogs.adapter as? SearchDogsListAdapter)?.submitList(emptyList())
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.invokeAction(SearchDogsViewModelContract.Action.FetchBreedsBySearchQuery(query))
                return false
            }
        })
    }

    override fun onDogClick(dogBreed: DogBreed) {
        viewModel.invokeAction(SearchDogsViewModelContract.Action.NavigateToDogDetails(dogBreed))
    }

    private fun performViewEvent(event: SearchDogsViewModelContract.Event) {
        when (event) {
            is SearchDogsViewModelContract.Event.NavigateToBreedDetailsView -> (activity as NavigateToFlow).navigateToFlow(NavigationFlow.DogsListToDogBreedDetailsFlow(event.dogBreed))
        }
    }

    private fun performViewState(state: SearchDogsViewModelContract.State) {
        renderVisibility(state)
        when (state) {
            is SearchDogsViewModelContract.State.SuccessState -> {
                (binding.rvSearchDogs.adapter as? SearchDogsListAdapter)?.submitList(emptyList())
                (binding.rvSearchDogs.adapter as? SearchDogsListAdapter)?.submitList(state.breedList)
            }
            is SearchDogsViewModelContract.State.ErrorState ->{
                Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

    private fun renderVisibility(state: SearchDogsViewModelContract.State) {
        binding.pbSearch.isVisible = state is SearchDogsViewModelContract.State.LoadingState
    }

    private fun configureRecyclerView() {
        if (binding.rvSearchDogs.adapter == null)
            binding.rvSearchDogs.adapter = SearchDogsListAdapter(this)

        if (binding.rvSearchDogs.layoutManager == null)
            binding.rvSearchDogs.layoutManager = LinearLayoutManager(requireContext())
    }
}