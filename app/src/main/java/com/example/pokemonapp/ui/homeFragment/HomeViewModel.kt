package com.example.pokemonapp.ui.homeFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.pokemonapp.data.datasource.database.entities.PokemonEntity
import com.example.pokemonapp.domain.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val pokemonRepository: PokemonRepository) : ViewModel() {

    private val _listpokemon = MutableLiveData<List<PokemonEntity>>()
    val listpokemon: LiveData<List<PokemonEntity>> get() = _listpokemon

    private val _onError = MutableLiveData<String>()
    val onError: LiveData<String> get() = _onError

    private val _progress = MutableStateFlow(true)
    val progress: StateFlow<Boolean> get() = _progress

    fun getListPokemon(offset: Int, limit: Int) {
        _progress.value = true
        pokemonRepository.getPokemonLocale(buildObserver(offset, limit))
    }

    fun updatePokemon(pokemonEntity: PokemonEntity) {
        pokemonRepository.update(pokemonEntity)
    }

    private fun buildObserver(offset: Int, limit: Int): Observer<List<PokemonEntity>> {
        return Observer { locale ->
            if (locale.isNotEmpty()) {
                _progress.value = false
                _listpokemon.value = locale
            } else {
                pokemonRepository.getPokemon(Observer { web ->
                    if (web.isNotEmpty()) {
                        _progress.value = false
                        _listpokemon.value = web
                    }
                }, buildError(), offset, limit)
            }
        }
    }

    private fun buildError(): Observer<String> {
        return Observer {
            if (it.isNotEmpty()) {
                _onError.value = it
            } else {
                _onError.value = "Algo salio mal intente más tarde!!"
            }
        }
    }
}