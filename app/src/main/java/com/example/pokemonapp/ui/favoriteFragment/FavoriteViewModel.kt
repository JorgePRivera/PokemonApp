package com.example.pokemonapp.ui.favoriteFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.pokemonapp.data.datasource.database.entities.PokemonEntity
import com.example.pokemonapp.domain.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(val pokemonRepository: PokemonRepository) :
    ViewModel() {

    private var _onPokemonFav = MutableLiveData<List<PokemonEntity>>()
    val onPokemonFav: LiveData<List<PokemonEntity>> get() = _onPokemonFav

    fun updatePokemon(pokemonEntity: PokemonEntity) {
        pokemonRepository.update(pokemonEntity)
    }

    fun getFavorite() {
        pokemonRepository.getFavorite(obtenerfav())
    }

    private fun obtenerfav(): Observer<List<PokemonEntity>> {
        return Observer {
            CoroutineScope(Dispatchers.Main).launch {
                _onPokemonFav.value = it
            }
        }
    }
}