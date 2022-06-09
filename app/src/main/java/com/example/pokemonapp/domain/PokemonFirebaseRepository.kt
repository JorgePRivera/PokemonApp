package com.example.pokemonapp.domain

import android.net.Uri
import androidx.lifecycle.Observer
import com.example.pokemonapp.data.datasource.database.entities.PokemonEntityFirebase
import com.example.pokemonapp.data.webDS.FireBaseWebDS
import com.google.firebase.database.DataSnapshot
import com.google.firebase.storage.UploadTask
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonFirebaseRepository @Inject constructor(private val fireBaseWebDS: FireBaseWebDS) {

    fun getPokemonFromFireBase(data: Observer<DataSnapshot>, isVisible: Observer<Boolean>) {
        fireBaseWebDS.getPokemonFromFireBase(data, isVisible)
    }

    fun insertStoregeToFireBase(
        uriPhoto: Uri,
        snapshot: Observer<UploadTask.TaskSnapshot>,
        idUri: Observer<String>,
        progress_string: Observer<String>,
        progreso: Observer<Double>,
        disableUi: Observer<Boolean>
    ) {
        fireBaseWebDS.insertStoregeToFireBase(uriPhoto, snapshot, idUri, progress_string, progreso, disableUi)
    }

    fun insertDataBaseToFiserBase(
        pokemon: PokemonEntityFirebase, observer: Observer<String>,
        isVisible: Observer<Boolean>, disableUi: Observer<Boolean>
    ) {
        fireBaseWebDS.insertDataBaseToFiserBase(pokemon, observer, isVisible, disableUi)
    }
}