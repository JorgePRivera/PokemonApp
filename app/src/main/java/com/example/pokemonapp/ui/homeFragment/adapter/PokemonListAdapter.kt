package com.example.pokemonapp.ui.homeFragment.adapter

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Filter
import android.widget.Filterable
import androidx.core.graphics.alpha
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.pokemonapp.R
import com.example.pokemonapp.data.datasource.database.entities.PokemonEntity
import com.example.pokemonapp.databinding.ItemListBinding
import com.example.pokemonapp.sys.util.Constats
import com.example.pokemonapp.ui.homeFragment.adapter.events.OnPokemonListener
import com.google.android.material.button.MaterialButton
import java.util.*
import kotlin.collections.ArrayList

class PokemonListAdapter(
    private var listaPokemon: List<PokemonEntity>,
    private var listener: OnPokemonListener
) : RecyclerView.Adapter<PokemonListAdapter.ViewHolder>(), Filterable {
    private lateinit var context: Context
    private var lastPosition = -1
    private var allPokemonList: List<PokemonEntity> = listaPokemon

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemListBinding.bind(view)

        fun setListener(pokemonEntity: PokemonEntity, img: View) {
            binding.container.setOnClickListener {
                listener.onClick(pokemonEntity, img)
            }
        }

        fun setListenerFAB(pokemonEntity: PokemonEntity) {
            binding.btnFavorite.setOnClickListener {
                listener.onClickFAB(pokemonEntity)
                setAnimation(
                    binding.btnFavorite,
                    if (pokemonEntity.isFavorite) R.drawable.ic_favorite_true
                    else R.drawable.ic_favorite_false,
                    R.raw.pokemon
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val pokemon = listaPokemon[position]

        with(viewHolder) {
            setListener(pokemon, binding.imgPhotoN)
            setListenerFAB(pokemon)
            binding.pokemon = pokemon
            setAnimation(viewHolder.itemView, position)
        }
    }

    override fun getItemCount(): Int = listaPokemon.size

    private fun setAnimation(itemView: View, position: Int) {
        if (position > lastPosition) {
            val animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left)
            itemView.animation = animation
            lastPosition = position
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    listaPokemon = allPokemonList
                } else {
                    val filtrarLista: ArrayList<PokemonEntity> = ArrayList()
                    for (row in listaPokemon) {
                        if (row.name.lowercase(Locale.ROOT)
                                .contains(charSearch.lowercase(Locale.ROOT))
                        ) {
                            filtrarLista.add(row)
                        }
                    }
                    listaPokemon = filtrarLista
                }
                val filterResults = FilterResults()
                filterResults.values = listaPokemon
                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(p0: CharSequence?, results: FilterResults?) {
                listaPokemon = results!!.values as ArrayList<PokemonEntity>
                notifyDataSetChanged()
            }
        }
    }

    private fun setAnimation(
        imageView: LottieAnimationView,
        drawableResource: Int, animation: Int,
    ) {
        imageView.setAnimation(animation)
        imageView.animate()
            .alpha(Constats.ALFA_ANIMATION)
            .setDuration(Constats.DURATION_ANIMATION)
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator?) {
                    imageView.isEnabled = false
                    imageView.playAnimation()
                }

                override fun onAnimationEnd(p0: Animator?) {
                    imageView.cancelAnimation()
                    imageView.alpha = Constats.ALFA_ANIMATION
                    imageView.setImageResource(drawableResource)
                    imageView.isEnabled = true
                }

                override fun onAnimationCancel(p0: Animator?) {
                    TODO("Not yet implemented")
                }

                override fun onAnimationRepeat(p0: Animator?) {
                    TODO("Not yet implemented")
                }
            })
    }
}