package com.example.pokemonapp.ui.homeFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.pokemonapp.data.datasource.database.entities.PokemonEntity
import com.example.pokemonapp.domain.PokemonRepository
import com.example.pokemonapp.sys.di.component.DaggerRepositoryComponent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class HomeViewModel : ViewModel() {
    @Inject
    lateinit var pokemonRepository: PokemonRepository

    private var _listpokemon = MutableLiveData<List<PokemonEntity>>()
    val listpokemon: LiveData<List<PokemonEntity>> get() = _listpokemon

    private var _onError = MutableLiveData<String>()
    val onError: LiveData<String> get() = _onError

    private var _progress = MutableStateFlow(true)
    val progress: StateFlow<Boolean> get() = _progress

    init {
        DaggerRepositoryComponent.builder()
            .build()
            .inject(this)
    }





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