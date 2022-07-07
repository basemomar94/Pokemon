package com.dadon.pokemon.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RoomMasterTable.TABLE_NAME
import com.dadon.pokemon.utilits.CONSTANTS
import com.dadon.pokemon.utilits.CONSTANTS.POKEMON_TABLE


@Entity(tableName = POKEMON_TABLE)
data class Pokemon(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val url: String,
    var image: String = ""
)