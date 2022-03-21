package com.example.pokemonapp.ui.favoriteFragment.adapter.listeners

import com.example.pokemonapp.data.datasource.database.entities.PokemonEntity

interface OnclickListenerFavorite {
    fun onClick(pokemonEntity: PokemonEntity)
}