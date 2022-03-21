package com.example.pokemonapp.data.datasource.web

import com.example.pokemonapp.data.datasource.web.response.PokemonResponse
import com.example.pokemonapp.sys.util.Constats
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServiceContract {
    @GET(Constats.API_URL + "?")
    //fun getPokemon(): Call<PokemonResponse>
    fun getPokemon(
        @Query("offset") offset: Int,
        @Query("limit") limites: Int
    ): Call<PokemonResponse>
}