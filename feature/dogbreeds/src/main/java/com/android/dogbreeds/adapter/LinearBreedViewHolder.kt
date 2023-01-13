package com.android.dogbreeds.adapter

import android.app.ActionBar
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.android.core_model.DogBreed
import com.android.dogbreeds.GlideApp
import com.android.dogbreeds.databinding.DogBreedItemBinding
import com.android.navigation.DogBreedHandler

class LinearBreedViewHolder(private val view: DogBreedItemBinding) : RecyclerView.ViewHolder(view.root) {

    fun onBindContent(content: DogBreed, handler: DogBreedHandler) {
        view.tvBreedName.text = content.name
        view.ivDog.scaleType = ImageView.ScaleType.FIT_XY

        val params: ViewGroup.LayoutParams = view.ivDog.layoutParams
        params.width = LinearLayout.LayoutParams.MATCH_PARENT
        params.height = 600

        view.ivDog.layoutParams = params

        GlideApp.with(view.root.context)
            .load(content.imageUrl)
            .into(view.ivDog)

        view.root.setOnClickListener {
            handler.onDogClick(content)
        }
    }
}