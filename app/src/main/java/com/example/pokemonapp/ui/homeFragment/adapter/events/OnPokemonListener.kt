package com.example.pokemonapp.ui.homeFragment.adapter.events

import android.view.View
import com.example.pokemonapp.data.datasource.database.entities.PokemonEntity

interface OnPokemonListener {
    fun onClick(pokemonEntity: PokemonEntity, view: View)
    fun onClickFAB(pokemonEntity: PokemonEntity)
}