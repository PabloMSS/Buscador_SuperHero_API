package com.example.buscador_superhero_api.ViewHolder

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.buscador_superhero_api.DetailSuperHeroActivity
import com.example.buscador_superhero_api.SuperheroItemResponse
import com.example.buscador_superhero_api.databinding.ActivitySuperHeroBinding
import com.example.buscador_superhero_api.databinding.ItemSuperheroBinding

class SuperheroViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private var binding = ItemSuperheroBinding.bind(view)

    fun bind(superheroItemResponse: SuperheroItemResponse){
        binding.tvSuperheroName.text = superheroItemResponse.name
        setImg(superheroItemResponse.image.url)

        binding.cvCrucero.setOnClickListener {
            var intent = Intent(binding.cvCrucero.context, DetailSuperHeroActivity::class.java)
            intent.putExtra("SuperheroId", superheroItemResponse.superheroId)
            binding.cvCrucero.context.startActivity(intent)
        }
    }

    fun setImg(url: String){
        Glide.with(binding.imgSuperhero.context).load(url).into(binding.imgSuperhero)
    }

}