package com.example.buscador_superhero_api.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.buscador_superhero_api.R
import com.example.buscador_superhero_api.Models.SuperheroItemResponse
import com.example.buscador_superhero_api.ViewHolder.SuperheroViewHolder

class SuperheroAdapter(var superheroList: List<SuperheroItemResponse> = emptyList(), private val onItemSelected:(String) -> Unit) : RecyclerView.Adapter<SuperheroViewHolder>(){

    fun updateList(superheroList: List<SuperheroItemResponse>){
        this.superheroList = superheroList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperheroViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_superhero, parent, false)
        return SuperheroViewHolder(view)
    }

    override fun getItemCount(): Int {
        return superheroList.size
    }

    override fun onBindViewHolder(viewHolder: SuperheroViewHolder, position: Int) {
        viewHolder.bind(superheroList[position], onItemSelected)
    }
}