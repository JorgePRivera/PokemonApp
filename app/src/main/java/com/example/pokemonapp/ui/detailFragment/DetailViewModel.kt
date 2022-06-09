package com.example.pokemonapp.ui.detailFragment

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
class DetailViewModel @Inject constructor(private val pokemonRepository: PokemonRepository) :
    ViewModel() {

    private var _onFromId = MutableLiveData<PokemonEntity>()
    val onFromId: LiveData<PokemonEntity> get() = _onFromId

    fun getFromId(id: Int) {
        pokemonRepository.getFronId(buildObserver(), id)
    }

    private fun buildObserver(): Observer<PokemonEntity> {
        return Observer {
            CoroutineScope(Dispatchers.Main).launch {
                _onFromId.value = it
            }
        }
    }
}