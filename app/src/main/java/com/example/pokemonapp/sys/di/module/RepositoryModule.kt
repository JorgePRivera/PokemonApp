package com.example.pokemonapp.sys.di.module

import com.example.pokemonapp.domain.PokemonRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun providePokemonRepository() = PokemonRepository()
}