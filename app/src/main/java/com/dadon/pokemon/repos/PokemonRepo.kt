package com.dadon.pokemon.repos

import android.content.Context
import com.dadon.pokemon.data.local.PokemonDatabase
import com.dadon.pokemon.data.remote.ServiceApi
import com.dadon.pokemon.models.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PokemonRepo(private val serviceApi: ServiceApi, private val context: Context) {

    val db = PokemonDatabase.getInstance(context)

    suspend fun getAllPokemon() = withContext(Dispatchers.IO) {
        serviceApi.getAllPokemon("150")
    }


    suspend fun addFavorite(pokemon: Pokemon) {
        db.pokemonDao().addFavorite(pokemon)
    }


}