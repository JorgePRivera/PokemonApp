package com.example.pokemonapp.sys.hilt

import com.example.pokemonapp.data.datasource.database.dao.PokemonDAO
import com.example.pokemonapp.data.db.PokemonDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [DataBaseModuleHilt::class])
@InstallIn(SingletonComponent::class)
object ModuleDAOHilt {

    @Provides
    @Singleton
    fun providePokemonDAO(pokemonDataBase: PokemonDataBase): PokemonDAO {
        return pokemonDataBase.pokemonDao()
    }
}