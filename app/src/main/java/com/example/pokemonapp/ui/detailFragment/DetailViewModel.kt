package com.example.pokemonapp.ui.detailFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.pokemonapp.data.datasource.database.entities.PokemonEntity
import com.example.pokemonapp.domain.PokemonRepository
import com.example.pokemonapp.sys.di.component.DaggerRepositoryComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.lifecycle.ViewModel

class DetailViewModel : ViewModel() {
    @Inject
    lateinit var pokemonRepository: PokemonRepository

    private var _onFromId = MutableLiveData<PokemonEntity>()
    val onFromId: LiveData<PokemonEntity> get() = _onFromId

    init {
        DaggerRepositoryComponent.builder()
            .build()
            .inject(this)
    }

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