package com.android.searchdogs

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.android.core_model.DogBreed
import com.android.navigation.DogBreedHandler
import com.android.searchdogs.databinding.SearchDogsItemBinding

class SearchDogsViewHolder(private val view: SearchDogsItemBinding) : RecyclerView.ViewHolder(view.root) {

    fun onBindContent(content: DogBreed, handler: DogBreedHandler) {
        view.tvBreedName.text = content.name
        view.tvBreedGroup.text = content.breedGroup
        view.tvBreedOrigin.text = content.origin
        view.tvBreedTemperament.text = content.temperament

        view.tvBreedName.isVisible = !content.name.isNullOrEmpty()
        view.tvBreedGroup.isVisible = !content.breedGroup.isNullOrEmpty()
        view.tvBreedOrigin.isVisible = !content.origin.isNullOrEmpty()
        view.tvBreedTemperament.isVisible = !content.temperament.isNullOrEmpty()
        view.tvBreedNameLabel.isVisible = !content.name.isNullOrEmpty()
        view.tvBreedGroupLabel.isVisible = !content.breedGroup.isNullOrEmpty()
        view.tvBreedOriginLabel.isVisible = !content.origin.isNullOrEmpty()
        view.tvBreedTemperamentLabel.isVisible = !content.temperament.isNullOrEmpty()

        view.root.setOnClickListener {
            handler.onDogClick(content)
        }
    }
}