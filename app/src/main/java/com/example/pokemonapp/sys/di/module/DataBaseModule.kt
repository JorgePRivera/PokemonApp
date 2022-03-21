package com.example.pokemonapp.sys.di.module

import android.content.Context
import androidx.room.Room
import com.example.pokemonapp.data.db.PokemonDataBase
import com.example.pokemonapp.sys.util.Constats
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ContextModule::class])
class DataBaseModule {
    @Singleton
    @Provides
    fun provideDataBase(context: Context?): PokemonDataBase {
        return Room.databaseBuilder(context!!, PokemonDataBase::class.java, Constats.DATABASE_NAME)
            .build()
    }
}