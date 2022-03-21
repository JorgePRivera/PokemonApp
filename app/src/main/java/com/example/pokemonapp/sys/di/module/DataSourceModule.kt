package com.example.pokemonapp.sys.di.module

import com.example.pokemonapp.data.datasource.database.dao.PokemonDAO
import com.example.pokemonapp.data.db.PokemonDataBase
import com.example.pokemonapp.data.webDS.PokemonWebDS
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [DataBaseModule::class])
class DataSourceModule {
    // TODO: WEB_DS

    @Singleton
    @Provides
    fun providePokemonWebDS() = PokemonWebDS()

    // TODO: ROOM

    @Singleton
    @Provides
    fun providePokemonDAO(pokemonDataBase: PokemonDataBase): PokemonDAO {
        return pokemonDataBase.pokemonDao()
    }


}