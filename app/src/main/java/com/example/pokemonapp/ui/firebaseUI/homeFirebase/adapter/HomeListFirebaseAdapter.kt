package com.example.pokemonapp.ui.firebaseUI.homeFirebase.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonapp.R
import com.example.pokemonapp.data.datasource.database.entities.PokemonEntityFirebase
import com.example.pokemonapp.databinding.ItemListFirebaseBinding
import com.example.pokemonapp.ui.firebaseUI.homeFirebase.adapter.events.HomeListFirebaseListener

class HomeListFirebaseAdapter(
    private var listaPokemon: List<PokemonEntityFirebase>,
    private var listener: HomeListFirebaseListener
) : RecyclerView.Adapter<HomeListFirebaseAdapter.ViewHolder>() {
    private var lastPosition = -1
    private lateinit var context: Context

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemListFirebaseBinding.bind(view)

        fun setListener(pokemonEntityFirebase: PokemonEntityFirebase) {
            binding.containerF.setOnClickListener {
                listener.onClick(pokemonEntityFirebase)
            }
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeListFirebaseAdapter.ViewHolder {
        context = parent.context
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_firebase, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeListFirebaseAdapter.ViewHolder, position: Int) {
       val pokemon = listaPokemon[position]

        with(holder){
            setListener(pokemon)
            binding.pokemon = pokemon
            setAnimation(holder.itemView, position)
        }
    }

    override fun getItemCount(): Int = listaPokemon.size

    fun setAnimation(itemView: View, position: Int) {
        if (position > lastPosition) {
            val animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left)
            itemView.animation = animation
            lastPosition = position
        }
    }
}