package com.example.pokemonapp.sys.util

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.transition.TransitionInflater
import com.example.pokemonapp.R
import java.io.IOException
import java.net.UnknownHostException

object Constats {
    const val EXT_PNG = ".png"
    const val API_URL = "https://pokeapi.co/api/v2/pokemon-form/"
    const val URL_PHOTO_NORMAL = "https://www.serebii.net/pokemongo/pokemon/"
    const val URL_PHOTO_SHINY = "https://www.serebii.net/pokemongo/pokemon/shiny/"

    const val PATH_FIREBASE_BD = "pokemonFirebaseBD"
    const val PATH_FIREBASE_STORAGE = "pokemonFirebaseStorage"

    /* const val URL_PHOTO_NORMAL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"
    const val URL_PHOTO_SHINY ="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/"
    */

    const val TABLE_NAME = "Pokemon"
    const val DATABASE_NAME = "Pokemon_database"
    private const val URL_CONECTION = "64.233.177.139"
    private var ORIGEN_ON_BACK: Int = 0

    const val ORIGEN_HOME: Int = 1
    const val ORIGEN_DETAIL: Int = 2
    const val ORIGEN_FAVORITES: Int = 3
    const val ORIGEN_FIREBASE: Int = 4
    const val ORIGEN_ADDFIREBASE: Int = 5
    const val IN_PROGRESS: Int = 6

    @OrigenOnBack
    fun getOrigen(): Int {
        return ORIGEN_ON_BACK
    }

    @SuppressLint("SupportAnnotationUsage")
    @OrigenOnBack
    fun setOrigen(@OrigenOnBack origen: Int) {
        ORIGEN_ON_BACK = origen
    }

    fun showFragment(fragment: Fragment, fragmentManager: FragmentManager) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmetContainer, fragment)
            .commit()
    }

    const val DURATION_ANIMATION: Long = 667
    const val ALFA_ANIMATION: Float = 1f

    fun isConection(): Boolean {
        val runtime = Runtime.getRuntime()
        try {
            val ipProcess = runtime.exec("/system/bin/ping -c 1 $URL_CONECTION")
            val exitValue = ipProcess.waitFor()
            ipProcess.destroy()
            return exitValue == 0
        } catch (e: UnknownHostException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        return false
    }
}