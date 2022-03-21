package com.example.pokemonapp.ui.homeFragment.adapter.events

import com.example.pokemonapp.data.datasource.database.entities.PokemonEntity

interface OnPokemonListener {
    fun onClick(pokemonEntity: PokemonEntity)
    fun onClickFAB(pokemonEntity: PokemonEntity)
}