package com.example.pokemonapp.sys.di.component

import android.content.Context
import com.example.pokemonapp.sys.di.module.UtilModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [UtilModule::class])
interface UtilComponent {
    fun getAppContext(): Context?
}