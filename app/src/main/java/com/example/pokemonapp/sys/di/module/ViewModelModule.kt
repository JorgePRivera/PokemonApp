package com.example.pokemonapp.sys.di.module

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.pokemonapp.ui.detailFragment.DetailViewModel
import com.example.pokemonapp.ui.favoriteFragment.FavoriteViewModel
import com.example.pokemonapp.ui.homeFragment.HomeViewModel
import dagger.Module
import dagger.Provides


@Module(includes = [ContextModule::class])
class ViewModelModule {
    @Provides
    fun providesHomeViewModel(frag: Fragment?): HomeViewModel {
        return ViewModelProvider(frag!!)[HomeViewModel::class.java]
    }

    @Provides
    fun providesdetailViewModel(frag: Fragment?): DetailViewModel {
        return ViewModelProvider(frag!!)[DetailViewModel::class.java]
    }


    @Provides
    fun providesfavoriteViewModel(frag: Fragment?): FavoriteViewModel {
        return ViewModelProvider(frag!!)[FavoriteViewModel::class.java]
    }


}