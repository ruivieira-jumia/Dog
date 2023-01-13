package com.android.searchdogs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.android.core_model.DogBreed
import com.android.navigation.DogBreedHandler
import com.android.searchdogs.databinding.SearchDogsItemBinding

class SearchDogsListAdapter(private val handler: DogBreedHandler) : ListAdapter<DogBreed, SearchDogsViewHolder>(
    diffCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchDogsViewHolder {
        val binding = SearchDogsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchDogsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchDogsViewHolder, position: Int) {
        getItem(position)?.let { pair -> holder.onBindContent(pair, handler) }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<DogBreed>() {
            override fun areItemsTheSame(oldItem: DogBreed, newItem: DogBreed): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: DogBreed, newItem: DogBreed): Boolean =
                oldItem == newItem
        }
    }
}