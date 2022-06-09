package com.example.pokemonapp.ui.firebaseUI.homeFirebase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.pokemonapp.data.datasource.database.entities.PokemonEntityFirebase
import com.example.pokemonapp.domain.PokemonFirebaseRepository
import com.google.firebase.database.DataSnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ViewModelHomeFireBase @Inject constructor(private val pokemonFirebaseRepository: PokemonFirebaseRepository) :
    ViewModel() {
    private var _listPokemon: MutableList<PokemonEntityFirebase> = ArrayList()
    private var _listFirebase = MutableLiveData<List<PokemonEntityFirebase>>()

    private var _isvisble = MutableLiveData<Boolean>()
    val isvisble: LiveData<Boolean> get() = _isvisble


    val listFirebase: LiveData<List<PokemonEntityFirebase>> get() = _listFirebase


    fun getListaToFirebase() {
        pokemonFirebaseRepository.getPokemonFromFireBase(buildObserver(), buildObservervisible())
    }

    private fun buildObservervisible(): Observer<Boolean> {
        return Observer {
            _isvisble.value = it
        }
    }

    private fun buildObserver(): Observer<DataSnapshot> {
        return Observer {
            _listPokemon.clear()
            for (groupsSnapShot in it.children) {
                val entity: PokemonEntityFirebase =
                    groupsSnapShot.getValue(PokemonEntityFirebase::class.java)!!
                entity.key = groupsSnapShot.key!!
                _listPokemon.add(entity)
            }
            _listFirebase.value = _listPokemon
        }
    }
}