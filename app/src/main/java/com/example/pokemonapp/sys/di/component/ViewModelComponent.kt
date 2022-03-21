package com.example.pokemonapp.sys.di.component

import com.example.pokemonapp.sys.di.module.ViewModelModule
import com.example.pokemonapp.ui.detailFragment.DetailFragment
import com.example.pokemonapp.ui.detailFragment.DetailViewModel
import com.example.pokemonapp.ui.favoriteFragment.FavoriteFragment
import com.example.pokemonapp.ui.homeFragment.HomeFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ViewModelModule::class])
interface ViewModelComponent {
    fun inject(homeFragment: HomeFragment)

    fun inject(detailFragment: DetailFragment)

    fun inject(favoriteFragment: FavoriteFragment)
}