package com.example.buscador_superhero_api

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.buscador_superhero_api.Adapter.SuperheroAdapter
import com.example.buscador_superhero_api.databinding.ActivitySuperHeroBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SuperHeroActivity : AppCompatActivity() {

    private lateinit var  binding: ActivitySuperHeroBinding
    private lateinit var retrofit: Retrofit

    private lateinit var adapter: SuperheroAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuperHeroBinding.inflate(layoutInflater)
        retrofit = getRetrofit()
        setContentView(binding.root)
        initUI()
    }

    fun initUI(){
        adapter = SuperheroAdapter()
        binding.rvSuperHero.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvSuperHero.adapter = adapter
        binding.svSuperHero.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchByName(query.orEmpty())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    fun searchByName(query: String){
        binding.progressBar.isVisible = true
        //Para lanzar una corrutina y toda lo q se hace dentro de las llaves, se hace en un hilo secundario
        CoroutineScope(Dispatchers.IO).launch{
            val myResponse: Response<SuperHeroDataResponse> = retrofit.create(ApiService::class.java).getSuperHeroes(query)
            if(myResponse.isSuccessful){
                var response = myResponse.body()
                if (response != null){
                    runOnUiThread { //Realiza la función en el Hilo Principal
                        adapter.updateList(response.superheroes)
                        binding.progressBar.isVisible = false
                    }
                    Log.i("pablo", response.toString())
                }
            }else{
                Log.i("pablo", "No Funciona")
            }
        }
    }

    fun getRetrofit(): Retrofit{
        val retrofit = Retrofit
            .Builder()
            .baseUrl("https://superheroapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit
    }
}