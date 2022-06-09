package com.example.pokemonapp.domain

import androidx.lifecycle.Observer
import com.example.pokemonapp.data.datasource.database.dao.PokemonDAO
import com.example.pokemonapp.data.datasource.database.entities.PokemonEntity
import com.example.pokemonapp.data.webDS.PokemonWebDS
import com.example.pokemonapp.sys.util.Constats
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRepository @Inject constructor(
    private val pokemonWebDS: PokemonWebDS,
    private val pokemonDAO: PokemonDAO
) {
    fun getFavorite(observer: Observer<List<PokemonEntity>>) {
        obtenerFavorite(observer)
    }

    fun getFronId(observer: Observer<PokemonEntity>, id: Int) {
        getPokemonFromId(observer, id)
    }

    fun getPokemonLocale(observer: Observer<List<PokemonEntity>>) {
        getDataLocale(observer)
    }

    fun update(pokemonEntity: PokemonEntity) {
        updatePokemon(pokemonEntity)
    }

    fun getPokemon(
        observer: Observer<List<PokemonEntity>>,
        error: Observer<String>,
        offset: Int,
        limit: Int
    ) {
        if (Constats.isConection()) {
            pokemonWebDS.requestPokemonApi(buildObserver(observer, error), error, offset, limit)
        } else {
            getDataLocale(observer)
        }
    }

    private fun buildObserver(
        observer: Observer<List<PokemonEntity>>,
        error: Observer<String>
    ): Observer<List<PokemonEntity>> {
        return Observer {
            if (it.isEmpty()) {
                error.onChanged("Lista vacia!")
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    var i = 0
                    for (pokemon in it) {
                        i++
                        if (i < 100) {
                            if (i < 10) {
                                pokemon.photoURL =
                                    "${Constats.URL_PHOTO_NORMAL}00$i${Constats.EXT_PNG}"
                                pokemon.photoUrlShiny =
                                    "${Constats.URL_PHOTO_SHINY}00$i${Constats.EXT_PNG}"
                            } else {
                                pokemon.photoURL =
                                    "${Constats.URL_PHOTO_NORMAL}0$i${Constats.EXT_PNG}"
                                pokemon.photoUrlShiny =
                                    "${Constats.URL_PHOTO_SHINY}0$i${Constats.EXT_PNG}"
                            }
                        } else {
                            pokemon.photoURL =
                                "${Constats.URL_PHOTO_NORMAL}$i${Constats.EXT_PNG}"
                            pokemon.photoUrlShiny =
                                "${Constats.URL_PHOTO_SHINY}$i${Constats.EXT_PNG}"
                        }
                        pokemonDAO.insertar(pokemon)

                    }
                    getDataLocale(observer)
                }
            }
        }
    }

    private fun getDataLocale(observer: Observer<List<PokemonEntity>>) {
        CoroutineScope(Dispatchers.IO).launch {
            val pokemon = pokemonDAO.getAll()
            withContext(Dispatchers.Main) {
                observer.onChanged(pokemon)
            }
        }
    }

    private fun updatePokemon(pokemonEntity: PokemonEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            pokemonDAO.update(pokemonEntity)
        }
    }

    private fun getPokemonFromId(observer: Observer<PokemonEntity>, id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val pokemonEntity = pokemonDAO.getPokemonFromId(id)
            observer.onChanged(pokemonEntity)
        }
    }

    private fun obtenerFavorite(observer: Observer<List<PokemonEntity>>) {
        CoroutineScope(Dispatchers.IO).launch {
            val movieEntity = pokemonDAO.getFavorite(true)
            observer.onChanged(movieEntity)
        }
    }
}