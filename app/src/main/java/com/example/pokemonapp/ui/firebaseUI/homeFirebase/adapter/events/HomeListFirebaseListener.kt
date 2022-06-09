package com.example.pokemonapp.ui.firebaseUI.homeFirebase.adapter.events

import com.example.pokemonapp.data.datasource.database.entities.PokemonEntityFirebase

interface HomeListFirebaseListener {
    fun onClick(pokemonEntityFirebase: PokemonEntityFirebase)
}