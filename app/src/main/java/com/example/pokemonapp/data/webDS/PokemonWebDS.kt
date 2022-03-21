package com.example.pokemonapp.data.webDS

import androidx.lifecycle.Observer
import com.example.pokemonapp.data.datasource.database.entities.PokemonEntity
import com.example.pokemonapp.data.datasource.web.WebServiceContract
import com.example.pokemonapp.data.datasource.web.response.PokemonResponse
import com.example.pokemonapp.sys.di.component.DaggerFrameworkComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class PokemonWebDS {
    @Inject
    lateinit var webServiceContract: WebServiceContract
    lateinit var call: Call<PokemonResponse>

    init {
        DaggerFrameworkComponent.builder()
            .build()
            .inject(this)
    }

    fun requestPokemonApi(
        observer: Observer<List<PokemonEntity>>,
        error: Observer<String>,
        offset: Int,
        limit: Int
    ) {
        call = webServiceContract.getPokemon(offset, limit)

        if (!call.isExecuted) {
            CoroutineScope(Dispatchers.IO).launch {
                call.enqueue(object : Callback<PokemonResponse> {
                    override fun onResponse(
                        call: Call<PokemonResponse>,
                        response: Response<PokemonResponse>
                    ) {
                        when (response.code()) {
                            200 -> {
                                if (response.body()?.results!!.isNotEmpty()) {
                                    observer.onChanged(response.body()?.results)
                                } else {
                                    error.onChanged(
                                        response.message()
                                    )
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<PokemonResponse>, t: Throwable) {
                        error.onChanged(t.message.toString())
                    }
                })
            }
        }
    }
}