package com.example.pokemonapp.sys.util

import java.io.IOException
import java.net.UnknownHostException

object Constats {
    const val EXT_PNG = ".png"
    const val API_URL = "https://pokeapi.co/api/v2/pokemon-form/"
    const val URL_PHOTO_NORMAL = "https://www.serebii.net/pokemongo/pokemon/"
    const val URL_PHOTO_SHINY = "https://www.serebii.net/pokemongo/pokemon/shiny/"

    /* const val URL_PHOTO_NORMAL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"
    const val URL_PHOTO_SHINY ="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/"
    */

    const val TABLE_NAME = "Pokemon"
    const val DATABASE_NAME = "Pokemon_database"
    private const val URL_CONECTION = "64.233.177.139"
    var ORIGEN: Int = 0
    var ORIGEN_HOME: Int = 1
    var ORIGEN_DETAIL: Int = 2
    var ORIGEN_FAVORITES: Int = 3

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