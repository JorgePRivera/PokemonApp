package com.example.pokemonapp

import android.app.Application
import android.content.Context
import com.example.pokemonapp.sys.di.component.DaggerUtilComponent
import com.example.pokemonapp.sys.di.component.UtilComponent
import com.example.pokemonapp.sys.di.module.ContextModule

class MainApplication : Application() {
    companion object {
        lateinit var utilComponent: UtilComponent

        fun getContext(): Context? = utilComponent.getAppContext()
    }

    override fun onCreate() {
        utilComponent =
            DaggerUtilComponent.builder()
                .contextModule(ContextModule(applicationContext))
                .build()
        super.onCreate()
    }
}