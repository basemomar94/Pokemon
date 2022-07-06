package com.dadon.pokemon.data.remote

import com.dadon.pokemon.utilits.CONSTANTS.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {


    fun getRetrofitBuilder(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
}