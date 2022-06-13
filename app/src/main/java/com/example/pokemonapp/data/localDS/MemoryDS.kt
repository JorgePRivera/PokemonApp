package com.example.pokemonapp.data.localDS

import androidx.lifecycle.Observer
import com.example.pokemonapp.data.datasource.database.dao.PokemonDAO
import com.example.pokemonapp.data.datasource.database.entities.PokemonEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MemoryDS @Inject constructor(private val pokemonDAO: PokemonDAO) {
    fun insertPokemon(pokemon: PokemonEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            pokemonDAO.insertar(pokemon)
        }
    }

    fun getDataLocale(observer: Observer<List<PokemonEntity>>) {
        CoroutineScope(Dispatchers.IO).launch {
            val pokemon = pokemonDAO.getAll()
            withContext(Dispatchers.Main) {
                observer.onChanged(pokemon)
            }
        }
    }

    fun updatePokemon(pokemonEntity: PokemonEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            pokemonDAO.update(pokemonEntity)
        }
    }

    fun getPokemonFromId(observer: Observer<PokemonEntity>, id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val pokemonEntity = pokemonDAO.getPokemonFromId(id)
            observer.onChanged(pokemonEntity)
        }
    }

    fun getFavorite(observer: Observer<List<PokemonEntity>>) {
        CoroutineScope(Dispatchers.IO).launch {
            val movieEntity = pokemonDAO.getFavorite(true)
            observer.onChanged(movieEntity)
        }
    }
}