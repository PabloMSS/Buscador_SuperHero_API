package com.example.buscador_superhero_api

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.buscador_superhero_api.Models.PowerStatsResponse
import com.example.buscador_superhero_api.Models.SuperHeroDetailResponse
import com.example.buscador_superhero_api.databinding.ActivityDetailSuperHeroBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.roundToInt

class DetailSuperHeroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailSuperHeroBinding
    private lateinit var retrofit: Retrofit

    companion object{
        const val Extra_Id = "extra_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSuperHeroBinding.inflate(layoutInflater)
        retrofit = getRetrofit()
        setContentView(binding.root)
        initUI()
    }

    fun initUI(){
        var idSuperHero = getId()
        getSuperHero(idSuperHero)
    }

    fun getId(): String{
        return intent.getStringExtra(Extra_Id).orEmpty()
    }

    fun getSuperHero(id: String){
        CoroutineScope(Dispatchers.IO).launch {
            val myResponse: Response<SuperHeroDetailResponse> = retrofit.create(ApiService::class.java).getSuperHeroeId(id)
            var response = myResponse.body()
            if(response != null){
                runOnUiThread {
                    createUI(response)
                }
            }
        }
    }

    fun createUI(superHero: SuperHeroDetailResponse){
        binding.tvSuperHeroName.text = superHero.name
        binding.tvSuperHeroFullName.text = superHero.biography.fullName
        binding.tvSuperHeroPublisher.text = superHero.biography.publisher
        Glide.with(this).load(superHero.image.url).into(binding.imgSuperheroDetail)
        prepareStats(superHero.powerstart)
    }

    fun prepareStats(powerstats: PowerStatsResponse){
        Log.i("prueba", powerstats.intelligence)
        updateHeight(binding.viewIntelligence, powerstats.durability)
        updateHeight(binding.viewStrength, powerstats.strength)
        updateHeight(binding.viewSpeed, powerstats.speed)
        updateHeight(binding.viewDurability, powerstats.durability)
        updateHeight(binding.viewPower, powerstats.power)
        updateHeight(binding.viewCombat, powerstats.combat)
    }

    fun updateHeight(view: View, valorHeight: String){
        val params = view.layoutParams
        params.height = pxToDp(valorHeight.toFloat())
        binding.viewCombat.layoutParams = params
    }

    fun pxToDp(px: Float):Int{
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, resources.displayMetrics).roundToInt()
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