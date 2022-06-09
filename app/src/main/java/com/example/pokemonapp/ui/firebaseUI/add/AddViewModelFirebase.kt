package com.example.pokemonapp.ui.firebaseUI.add

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.pokemonapp.data.datasource.database.entities.PokemonEntityFirebase
import com.example.pokemonapp.domain.PokemonFirebaseRepository
import com.google.firebase.storage.UploadTask
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddViewModelFirebase @Inject constructor(private val pokemonFirebaseRepository: PokemonFirebaseRepository) :
    ViewModel() {
    private var _insert = MutableLiveData<String>()
    val insert: LiveData<String> get() = _insert

    private var _progress_string = MutableLiveData<String>()
    val progress_string: LiveData<String> get() = _progress_string

    private var _progress_double = MutableLiveData<Double>()
    val progress_double: LiveData<Double> get() = _progress_double

    private var _photoUriFb = MutableLiveData<String>()
    val photoUriFb: LiveData<String> get() = _photoUriFb

    private var _photoUri = MutableLiveData<String>()
    val photoUri: LiveData<String> get() = _photoUri

    private var _photoUriShiny = MutableLiveData<String>()
    val photoUriShiny: LiveData<String> get() = _photoUriShiny

    private var _uriPhoto = MutableLiveData<Uri>()
    val uriPhoto: LiveData<Uri> get() = _uriPhoto

    private var _isVisibleProgress = MutableLiveData<Boolean>()
    val isVisibleProgress: LiveData<Boolean> get() = _isVisibleProgress

    private var _disableUI = MutableLiveData<Boolean>()
    val disableUI: LiveData<Boolean> get() = _disableUI

    // TODO: metodos

    fun loadphoto(photo: String, opcion: Int) {
        if (opcion == 0) {
            _photoUri.value = photo
        } else {
            _photoUriShiny.value = photo
        }
    }

    fun insertDataBaseToFiserBase(pokemonEntityFirebase: PokemonEntityFirebase) {
        pokemonFirebaseRepository.insertDataBaseToFiserBase(
            pokemonEntityFirebase, construirObserver(),
            buildIsVisible(), buildDisableUi()
        )
    }

    private fun construirObserver(): Observer<String> {
        return Observer {
            _insert.value = it
        }
    }

    fun insertStoregeToFireBase(uriPhoto: Uri) {
        pokemonFirebaseRepository.insertStoregeToFireBase(
            uriPhoto,
            buildStorage(),
            buldString(),
            buildProgress_string(),
            buildProgresso(),
            buildDisableUi()
        )
    }

    private fun buildDisableUi(): Observer<Boolean> {
        return Observer {
            _disableUI.value = it
        }
    }

    private fun buildProgresso(): Observer<Double> {
        return Observer {
            _progress_double.value = it
        }
    }

    private fun buildProgress_string(): Observer<String> {
        return Observer {
            _progress_string.value = it
        }
    }

    private fun buildStorage(): Observer<UploadTask.TaskSnapshot> {
        return Observer {
            it.storage.downloadUrl.addOnSuccessListener { uri ->
                _uriPhoto.value = uri
            }
        }
    }

    private fun buldString(): Observer<String> {
        return Observer {
            _photoUriFb.value = it
        }
    }

    private fun buildIsVisible(): Observer<Boolean> {
        return Observer {
            _isVisibleProgress.value = it
        }
    }

}