package com.dadon.pokemon.data.remote

import com.dadon.pokemon.models.Abilities
import com.dadon.pokemon.models.Description
import com.dadon.pokemon.models.PokemonResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceApi {


    @GET("pokemon")
    suspend fun getAllPokemon(
        @Query("limit") limit: String,
    ): Response<PokemonResult>

    @GET("pokemon-species/{id}")
    suspend fun getPokemonInfo(
        @Path("id") id: String
    ): Response<Description>


    @GET("pokemon/{id}")
    suspend fun getStats(
        @Path("id") id: String
    ): Response<Abilities>

}