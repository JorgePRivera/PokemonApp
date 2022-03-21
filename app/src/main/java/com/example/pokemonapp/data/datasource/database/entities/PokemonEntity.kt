package com.example.pokemonapp.data.datasource.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pokemonapp.sys.util.Constats
import com.google.gson.annotations.SerializedName

@Entity(tableName = Constats.TABLE_NAME)
data class PokemonEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String,
    var photoURL: String,
    var photoUrlShiny: String,
    var isFavorite: Boolean = false
)
