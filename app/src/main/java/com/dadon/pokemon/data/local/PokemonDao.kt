package com.dadon.pokemon.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.dadon.pokemon.models.Pokemon

@Dao
interface PokemonDao {

    @Insert(onConflict = REPLACE)
 suspend   fun addFavorite(pokemon: Pokemon)



    @Query("Select * from POKEMON_TABLE")
 suspend   fun getFavorites(): MutableList<Pokemon>

 @Delete
 suspend fun removeFavorite(pokemon: Pokemon)
}