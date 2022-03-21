package com.example.pokemonapp.sys.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dagger.Module
import dagger.Provides

@Module
class ContextModule constructor(con: Context?) {
    private var activity: AppCompatActivity? = null
    private var fragment: Fragment? = null
    private var context: Context? = con

    constructor(act: AppCompatActivity) : this(null) {
        activity = act
    }

    constructor(fra: Fragment) : this(null) {
        fragment = fra
    }

    @Provides
    fun getAppCompatActivity() = activity

    @Provides
    fun getFragment() = fragment

    @Provides
    fun getContext() = context


}