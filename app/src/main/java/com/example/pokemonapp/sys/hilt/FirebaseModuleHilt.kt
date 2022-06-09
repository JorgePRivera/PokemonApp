package com.example.pokemonapp.sys.hilt

import com.example.pokemonapp.sys.util.Constats
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModuleHilt {
    @Singleton
    @Provides
    fun provideFirebaseReferenceDataBase(): DatabaseReference {
        val firebaseReference = FirebaseDatabase.getInstance()
        return firebaseReference.getReference(Constats.PATH_FIREBASE_BD)
    }


    @Singleton
    @Provides
    fun provideFirebaseReferenceStorage(): StorageReference {
        val firebaseStorage = FirebaseStorage.getInstance()
        return firebaseStorage.getReference(Constats.PATH_FIREBASE_STORAGE)
    }
}