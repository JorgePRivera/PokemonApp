package com.example.pokemonapp.sys.di.component

import com.example.pokemonapp.data.webDS.PokemonWebDS
import com.example.pokemonapp.sys.di.module.FrameWorkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [FrameWorkModule::class])
interface FrameworkComponent {
    fun inject(pokemonWebDS: PokemonWebDS)
}