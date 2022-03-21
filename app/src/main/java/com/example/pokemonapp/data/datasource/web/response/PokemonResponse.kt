package com.example.pokemonapp.data.datasource.web.response

import com.example.pokemonapp.data.datasource.database.entities.PokemonEntity
import com.google.gson.annotations.SerializedName

data class PokemonResponse(
    @SerializedName("count") val count: Int,
    @SerializedName("next") val next: String,
    @SerializedName("previous") val previous: String,
    @SerializedName("results") val results: List<PokemonEntity>
)
