package com.dadon.pokemon.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dadon.pokemon.models.Pokemon
import com.dadon.pokemon.utilits.CONSTANTS.POKEMON_DATABASE


@Database(entities = [Pokemon::class], version = 1, exportSchema = false)
abstract class PokemonDatabase : RoomDatabase() {


    abstract fun pokemonDao(): PokemonDao


    companion object {

        @Volatile
        private var instance: PokemonDatabase? = null

        fun getInstance(context: Context): PokemonDatabase {
            return instance ?: synchronized(Any()) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }


        private fun buildDatabase(context: Context): PokemonDatabase {

            return Room.databaseBuilder(
                context.applicationContext, PokemonDatabase::class.java,
                POKEMON_DATABASE
            ).build()

        }


    }


}