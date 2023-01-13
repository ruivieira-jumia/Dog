package com.android.dogdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.android.core_model.DogBreed
import com.android.dogdetails.databinding.FragmentDogDetailsBinding
import com.android.navigation.NavigationArgs.DOG_BREED
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DogDetailsFragment : DialogFragment() {

    private lateinit var binding: FragmentDogDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentDogDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val breed: DogBreed? = arguments?.get(DOG_BREED) as? DogBreed?
        breed?.let {
            setUIViews(it)
        }
    }

    private fun setUIViews(breed: DogBreed) {
        breed.name?.let {
            binding.tvName.isVisible = true
            binding.tvName.text = getString(R.string.breed_name).plus(" ").plus(it)
        }
        breed.origin?.let {
            binding.tvOrigin.isVisible = true
            binding.tvOrigin.text = getString(R.string.origin).plus(" ").plus(it)
        }
        breed.breedGroup?.let {
            binding.tvCategory.isVisible = true
            binding.tvCategory.text = getString(R.string.group).plus(" ").plus(it)
        }
        breed.temperament?.let {
            binding.tvTemperament.isVisible = true
            binding.tvTemperament.text = getString(R.string.temperament).plus(" ").plus(it)
        }
    }
}