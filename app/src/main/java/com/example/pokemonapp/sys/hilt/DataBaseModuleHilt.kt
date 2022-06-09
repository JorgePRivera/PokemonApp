package com.example.pokemonapp.sys.hilt

import android.content.Context
import androidx.room.Room
import com.example.pokemonapp.data.datasource.database.dao.PokemonDAO
import com.example.pokemonapp.data.db.PokemonDataBase
import com.example.pokemonapp.sys.util.Constats
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModuleHilt {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): PokemonDataBase {
        return Room.databaseBuilder(context, PokemonDataBase::class.java, Constats.DATABASE_NAME)
            .build()
    }
}