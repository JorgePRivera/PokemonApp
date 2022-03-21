package com.example.pokemonapp.sys.di.component

import com.example.pokemonapp.domain.PokemonRepository
import com.example.pokemonapp.sys.di.module.DataSourceModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataSourceModule::class])
interface DataSourceComponent {
    fun inject(pokemonRepository: PokemonRepository)
}