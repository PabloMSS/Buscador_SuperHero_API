package com.example.buscador_superhero_api

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.buscador_superhero_api.databinding.ActivityDetailSuperHeroBinding
import com.example.buscador_superhero_api.databinding.ActivitySuperHeroBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class DetailSuperHeroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailSuperHeroBinding
    private lateinit var retrofit: Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_super_hero)
        binding = ActivityDetailSuperHeroBinding.inflate(layoutInflater)
        retrofit = getRetrofit()
        setContentView(binding.root)
        initUI()
    }

    fun initUI(){
        getSuperHero()
    }

    fun getId(): String{
        return intent.getStringExtra("SuperheroId").toString()
    }

    fun getSuperHero(){
        CoroutineScope(Dispatchers.IO).launch {
            val myResponse: Response<SuperheroItemResponse> = retrofit.create(ApiService::class.java).getSuperHeroeId(getId())
            var response = myResponse.body()
            if(response != null){
                binding.textoPrueba.text = response.name
            }
        }
    }

    fun getRetrofit(): Retrofit{
        var retrofit = Retrofit
            .Builder()
            .baseUrl("https://superheroapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit
    }
}