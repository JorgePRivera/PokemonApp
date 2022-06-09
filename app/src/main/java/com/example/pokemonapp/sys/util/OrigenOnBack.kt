package com.example.pokemonapp.sys.util

import androidx.annotation.IntDef

@IntDef(Constats.ORIGEN_HOME, Constats.ORIGEN_DETAIL, Constats.ORIGEN_FAVORITES, Constats.ORIGEN_FIREBASE, Constats.ORIGEN_ADDFIREBASE, Constats.IN_PROGRESS)
@Retention(AnnotationRetention.SOURCE)
annotation class OrigenOnBack()
