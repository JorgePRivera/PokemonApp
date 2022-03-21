package com.example.pokemonapp.ui.favoriteFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.pokemonapp.data.datasource.database.entities.PokemonEntity
import com.example.pokemonapp.domain.PokemonRepository
import com.example.pokemonapp.sys.di.component.DaggerRepositoryComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteViewModel : ViewModel() {
    @Inject
    lateinit var pokemonRepository: PokemonRepository

    private var _onPokemonFav = MutableLiveData<List<PokemonEntity>>()
    val onPokemonFav: LiveData<List<PokemonEntity>> get() = _onPokemonFav

    init {
        DaggerRepositoryComponent.builder()
            .build()
            .inject(this)
    }

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