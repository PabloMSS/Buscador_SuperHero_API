package com.example.buscador_superhero_api

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.buscador_superhero_api.Adapter.SuperheroAdapter
import com.example.buscador_superhero_api.Models.SuperHeroDataResponse
import com.example.buscador_superhero_api.Models.SuperheroItemResponse
import com.example.buscador_superhero_api.databinding.ActivitySuperHeroBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList
import kotlin.math.log

class SuperHeroActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySuperHeroBinding
    private lateinit var retrofit: Retrofit

    private lateinit var adapter: SuperheroAdapter

    var list_abecedario = ('A'..'Z').toList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuperHeroBinding.inflate(layoutInflater)
        retrofit = getRetrofit()
        getStartSuperHero()
        setContentView(binding.root)
        initUI()
    }

    fun initUI(){
        adapter = SuperheroAdapter(){ id -> navigateToDetail(id) }
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

    fun getStartSuperHero(){
        binding.progressBar.isVisible = true
        CoroutineScope(Dispatchers.IO).launch{
            var letra_aleatorio = (0 until list_abecedario.size).random()
            val myResponse: Response<SuperHeroDataResponse> = retrofit.create(ApiService::class.java).getSuperHeroes(list_abecedario[letra_aleatorio].toString())
            if(myResponse.isSuccessful){
                var response = myResponse.body()
                if (response != null){
                    runOnUiThread {
                        adapter.updateList(response.superheroes)
                        binding.progressBar.isVisible = false
                    }
                }
            }else{
                Log.i("prueba", "prueba")
            }
        }
    }

    fun searchByName(query: String){
        binding.progressBar.isVisible = true
        //Para lanzar una corrutina y toda lo que se hace dentro de las llaves, se hace en un hilo secundario
        CoroutineScope(Dispatchers.IO).launch{
            val myResponse: Response<SuperHeroDataResponse> = retrofit.create(ApiService::class.java).getSuperHeroes(query)
            if(myResponse.isSuccessful){
                var responseBody = myResponse.body()
                Log.i("prueba", responseBody?.response.toString())
                if (responseBody != null && !responseBody.response.equals("error")){
                    runOnUiThread { //Realiza la función en el Hilo Principal
                        adapter.updateList(responseBody.superheroes)
                        binding.progressBar.isVisible = false
                        binding.rvSuperHero.isVisible = true

                    }
                }else{
                    runOnUiThread {
                        var listEmply: List<SuperheroItemResponse> = listOf()
                        adapter.updateList(listEmply)
                        binding.progressBar.isVisible = false
                        binding.rvSuperHero.isVisible = false
                        var dialog = showDialog()
                        dialog.show()
                        GlobalScope.launch(Dispatchers.Main) {
                            delay(2500)
                            dialog.dismiss()
                        }
                    }
                }
            }
        }
    }

    fun showDialog(): Dialog {
        var dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_notfind)
        return dialog
    }

    fun getRetrofit(): Retrofit{
        val retrofit = Retrofit
            .Builder()
            .baseUrl("https://superheroapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit
    }

    fun navigateToDetail(id: String){
        var intent = Intent(this, DetailSuperHeroActivity::class.java)
        intent.putExtra(DetailSuperHeroActivity.Extra_Id, id)
        startActivity(intent)
    }
}