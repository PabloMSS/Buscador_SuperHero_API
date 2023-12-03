package com.example.buscador_superhero_api.ViewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.buscador_superhero_api.Models.SuperheroItemResponse
import com.example.buscador_superhero_api.databinding.ItemSuperheroBinding

class SuperheroViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private var binding = ItemSuperheroBinding.bind(view)

    fun bind(superheroItemResponse: SuperheroItemResponse, onItemSelected:(String) -> Unit){
        binding.tvSuperheroName.text = superheroItemResponse.name
        setImg(superheroItemResponse.image.url)

        binding.root.setOnClickListener {
            onItemSelected(superheroItemResponse.superheroId)
        }
    }

    fun setImg(url: String){
        Glide.with(binding.imgSuperhero.context).load(url).into(binding.imgSuperhero)
    }

}