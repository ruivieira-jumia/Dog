package com.android.dogbreeds.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.core_model.DogBreed
import com.android.dogbreeds.databinding.DogBreedItemBinding
import com.android.navigation.DogBreedHandler

class DogsListAdapter(private val handler: DogBreedHandler) : ListAdapter<DogBreed, RecyclerView.ViewHolder>(
    diffCallback
) {

    //I wouldn't recommed this implementation. To be honest i would like more to have a object called UIDogBreed with
    //the DogBreed object and flag, and use it in the adapter and I would updated if I had more time to work
    var isGrid = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            LINEAR_BREED -> {
                val binding = DogBreedItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                LinearBreedViewHolder(binding)
            }
            GRID_BREED -> {
                val binding = DogBreedItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                GridBreedViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Unknown Type")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LinearBreedViewHolder -> getItem(position)?.let { pair -> holder.onBindContent(pair, handler) }
            is GridBreedViewHolder -> getItem(position)?.let { pair -> holder.onBindContent(pair, handler) }
        }
    }

    override fun getItemViewType(position: Int): Int = if (isGrid) 2 else 1

    companion object {

        const val LINEAR_BREED = 1
        const val GRID_BREED = 2

        private val diffCallback = object : DiffUtil.ItemCallback<DogBreed>() {
            override fun areItemsTheSame(oldItem: DogBreed, newItem: DogBreed): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: DogBreed, newItem: DogBreed): Boolean =
                oldItem == newItem
        }
    }
}