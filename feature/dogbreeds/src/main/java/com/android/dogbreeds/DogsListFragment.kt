package com.android.dogbreeds

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.core_model.DogBreed
import com.android.dogbreeds.adapter.DogsListAdapter
import com.android.dogbreeds.databinding.FragmentDogsListBinding
import com.android.navigation.DogBreedHandler
import com.android.navigation.NavigateToFlow
import com.android.navigation.NavigationFlow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DogsListFragment : Fragment(), MenuProvider, DogBreedHandler {

    private val viewModel: DogsListViewModel by viewModels()
    private var scrollListener: RecyclerView.OnScrollListener? = null

    private lateinit var binding: FragmentDogsListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDogsListBinding.inflate(inflater, container, false)
        val menuHost: MenuHost? = activity
        menuHost?.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.uiStateData.observe(viewLifecycleOwner, Observer(::performViewState))
        viewModel.uiEventData.observe(viewLifecycleOwner, Observer(::performViewEvent))
        viewModel.invokeAction(DogsListViewModelContract.Action.InitPage)

        configureRecyclerView()
    }

    override fun onDogClick(dogBreed: DogBreed) {
        viewModel.invokeAction(DogsListViewModelContract.Action.NavigateToDogDetails(dogBreed))
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.top_app_bar, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.sort -> {
                viewModel.invokeAction(DogsListViewModelContract.Action.SortAlphabetically)
                true
            }
            R.id.change_view -> {
                viewModel.invokeAction(DogsListViewModelContract.Action.ChangeView)
                true
            }
            else -> false
        }
    }

    private fun performViewEvent(event: DogsListViewModelContract.Event) {
        when (event) {
            is DogsListViewModelContract.Event.NavigateToBreedDetailsView -> (activity as NavigateToFlow).navigateToFlow(NavigationFlow.DogsListToDogBreedDetailsFlow(event.dogBreed))
            is DogsListViewModelContract.Event.ChangeView -> configureRecyclerView(event.isGrid)
            is DogsListViewModelContract.Event.SortAlphabetically -> sortAlphabetically(event.sortedAlphabetically)
        }
    }

    private fun performViewState(state: DogsListViewModelContract.State) {
        renderScreenVisibility(state)
        when (state) {
            is DogsListViewModelContract.State.SuccessState -> {
                (binding.rvBreeds.adapter as? DogsListAdapter?)?.submitList(emptyList())
                (binding.rvBreeds.adapter as? DogsListAdapter?)?.submitList(state.breedList)
            }
            is DogsListViewModelContract.State.ErrorState ->
                Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()

        }
    }

    private fun renderScreenVisibility(state: DogsListViewModelContract.State) {
        binding.pbBreeds.isVisible = state is DogsListViewModelContract.State.LoadingState
    }

    private fun configureRecyclerView(isGrid: Boolean = false) {
        if (binding.rvBreeds.adapter == null)
            binding.rvBreeds.adapter = DogsListAdapter(this)

        if (binding.rvBreeds.layoutManager == null)
            binding.rvBreeds.layoutManager = GridLayoutManager(requireContext(), 2)

        configureScrollListener()
        (binding.rvBreeds.layoutManager as GridLayoutManager).spanCount = if (isGrid) 2 else 1
        (binding.rvBreeds.adapter as? DogsListAdapter)?.isGrid = isGrid
    }

    override fun onDestroyView() {
        super.onDestroyView()
        scrollListener = null
    }

    private fun configureScrollListener() {
        if (scrollListener == null) {
            scrollListener = object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!recyclerView.canScrollVertically(1))
                        viewModel.invokeAction(
                            DogsListViewModelContract.Action.FetchBreedsByPage(
                                (binding.rvBreeds.layoutManager as GridLayoutManager).findLastCompletelyVisibleItemPosition()
                            )
                        )
                }
            }
            binding.rvBreeds.addOnScrollListener(scrollListener as RecyclerView.OnScrollListener)
        }
    }

    private fun sortAlphabetically(sortedAlphabetically: Boolean) {
        if (((binding.rvBreeds.adapter as? DogsListAdapter?)?.itemCount ?: 0) > 0) {
            val sortedList = (binding.rvBreeds.adapter as? DogsListAdapter?)?.currentList?.sortedBy { it.name }
            (binding.rvBreeds.adapter as? DogsListAdapter?)?.submitList(if (sortedAlphabetically) sortedList else sortedList?.reversed()) {
                binding.rvBreeds.scrollToPosition(0)
            }
            if (sortedAlphabetically)
                Toast.makeText(requireContext(), "Updated Alphabetically", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(requireContext(), "Updated Descending Alphabetically", Toast.LENGTH_SHORT).show()
        }
    }
}