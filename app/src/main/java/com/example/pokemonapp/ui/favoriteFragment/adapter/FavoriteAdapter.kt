package com.example.pokemonapp.ui.favoriteFragment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonapp.R
import com.example.pokemonapp.data.datasource.database.entities.PokemonEntity
import com.example.pokemonapp.databinding.ItemListFavoriteBinding
import com.example.pokemonapp.ui.favoriteFragment.adapter.listeners.OnclickListenerFavorite

class FavoriteAdapter(
    private var listapokemon: List<PokemonEntity>,
    private var listener: OnclickListenerFavorite
) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
    private lateinit var context: Context


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemListFavoriteBinding.bind(view)

        fun setListener(movieEntity: PokemonEntity) {
            binding.cardContainer.setOnClickListener {
                listener.onClick(movieEntity)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_list_favorite, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val pokemon = listapokemon[position]

        with(viewHolder) {
            setListener(pokemon)
            binding.pokemon = pokemon
        }
    }

    override fun getItemCount(): Int = listapokemon.size
}