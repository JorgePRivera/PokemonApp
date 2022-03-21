package com.example.pokemonapp.sys.di.component

import com.example.pokemonapp.sys.di.module.RepositoryModule
import com.example.pokemonapp.ui.detailFragment.DetailViewModel
import com.example.pokemonapp.ui.favoriteFragment.FavoriteViewModel
import com.example.pokemonapp.ui.homeFragment.HomeViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RepositoryModule::class])
interface RepositoryComponent {
    fun inject(homeViewModel: HomeViewModel)
    fun inject(detailViewModel: DetailViewModel)
    fun inject(favoriteViewModel: FavoriteViewModel)
}