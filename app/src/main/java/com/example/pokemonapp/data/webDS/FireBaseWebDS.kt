package com.example.pokemonapp.data.webDS

import android.net.Uri
import android.util.Log
import androidx.lifecycle.Observer
import com.example.pokemonapp.data.datasource.database.entities.PokemonEntityFirebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FireBaseWebDS @Inject constructor(
    private val databaseReference: DatabaseReference,
    private val firebaseStorage: StorageReference,
) {
    fun getPokemonFromFireBase(data: Observer<DataSnapshot>, isVisible: Observer<Boolean>) {
        isVisible.onChanged(true)
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    data.onChanged(snapshot)
                    isVisible.onChanged(false)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("cancel", error.details)
                isVisible.onChanged(false)
            }
        })
    }

    fun insertStoregeToFireBase(
        uriPhoto: Uri,
        snapshot: Observer<UploadTask.TaskSnapshot>,
        idUri: Observer<String>,
        progress_message: Observer<String>,
        progreso: Observer<Double>,
        disableUi: Observer<Boolean>
    ) {
        val uriToFirebaseString = databaseReference.push().key!!

        firebaseStorage.child(uriToFirebaseString).putFile(uriPhoto)
            .addOnProgressListener { taskSnapshot ->
                progreso.onChanged(((100 * taskSnapshot.bytesTransferred) / taskSnapshot.totalByteCount).toDouble())
                progress_message.onChanged("In progress")
                disableUi.onChanged(false)
            }.addOnCompleteListener {
                progress_message.onChanged("Complete")
                if(!it.isSuccessful){
                    progress_message.onChanged("${it.exception.toString()}")
                }
            }.addOnSuccessListener {
                snapshot.onChanged(it)
                idUri.onChanged(uriToFirebaseString)
                progress_message.onChanged("Succes")
                disableUi.onChanged(true)
            }.addOnFailureListener {
                progreso.onChanged(0.0)
                progress_message.onChanged("Faild")
                disableUi.onChanged(true)
            }.addOnCanceledListener {
                progreso.onChanged(0.0)
                progress_message.onChanged("Cancel")
                disableUi.onChanged(true)
            }.addOnPausedListener {
                progress_message.onChanged("In pause")
                disableUi.onChanged(true)
            }
    }

    fun insertDataBaseToFiserBase(
        pokemon: PokemonEntityFirebase, observer: Observer<String>,
        isVisible: Observer<Boolean>,disableUi: Observer<Boolean>
    ) {
        isVisible.onChanged(true)
        disableUi.onChanged(false)
        CoroutineScope(Dispatchers.IO).launch {
            databaseReference.push().setValue(pokemon)
                .addOnCompleteListener {
                }.addOnSuccessListener {
                    observer.onChanged("Dato Insertado con Exito")
                    isVisible.onChanged(false)
                    disableUi.onChanged(true)
                }.addOnCanceledListener {
                    observer.onChanged("Error al Insertar Dato")
                    isVisible.onChanged(false)
                    disableUi.onChanged(true)
                }.addOnFailureListener {
                    observer.onChanged("Error al Insertar Dato")
                    isVisible.onChanged(false)
                    disableUi.onChanged(true)
                }
        }
    }
}