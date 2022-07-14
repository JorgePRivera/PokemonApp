package com.example.pokemonapp.sys.util

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.isGone
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.pokemonapp.MainApplication
import com.example.pokemonapp.R
import com.example.pokemonapp.ui.favoriteFragment.adapter.FavoriteAdapter
import com.example.pokemonapp.ui.firebaseUI.homeFirebase.adapter.HomeListFirebaseAdapter
import com.example.pokemonapp.ui.homeFragment.adapter.PokemonListAdapter
import de.hdodenhof.circleimageview.CircleImageView

lateinit var linear: LinearLayoutManager
lateinit var gridLayoutManager: GridLayoutManager

@BindingAdapter("loadImagefab")
fun LottieAnimationView.bindingFab(isFavorite: Boolean) {
    if (isFavorite) {
        this.setImageResource(R.drawable.ic_favorite_true)
    } else {
        this.setImageResource(R.drawable.ic_favorite_false)
    }
}


@BindingAdapter("loadimageCircle")
fun CircleImageView.bindingImageCircle(imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        this.visibility = this.rootView.visibility
        val options: RequestOptions = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .placeholder(R.drawable.ic_loading)

        Glide.with(this.context)
            .load(imageUrl)
            .apply(options)
            .into(this)
    } else {
        this.rootView.isGone
    }
}

@BindingAdapter("loadimage")
fun AppCompatImageView.bindingImageCircle(imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        this.visibility = this.rootView.visibility
        val options: RequestOptions = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .placeholder(R.drawable.ic_loading)

        Glide.with(this.context)
            .load(imageUrl)
            .apply(options)
            .into(this)
    } else {
        this.rootView.isGone
    }
}

@SuppressLint("ResourceAsColor")
@BindingAdapter("changeText")
fun TextView.bindingText(texto: String) {
    this.text = texto
}

@BindingAdapter("recyclerviewadapter")
fun RecyclerView.bindingRecyclerView(anyAdapter: Any?) {
    linear = LinearLayoutManager(context)
    gridLayoutManager = GridLayoutManager(context, 3)

    if (anyAdapter != null) {
        when (anyAdapter) {
            is PokemonListAdapter,
            is HomeListFirebaseAdapter -> {
                this.apply {
                    setHasFixedSize(true)
                    layoutManager = linear
                    adapter = anyAdapter as RecyclerView.Adapter<*>?
                }
            }
            is FavoriteAdapter -> {
                this.apply {
                    setHasFixedSize(true)
                    layoutManager = gridLayoutManager
                    adapter = anyAdapter
                }
            }
           /* is HomeListFirebaseAdapter -> {
                this.apply {
                    setHasFixedSize(true)
                    layoutManager = linear
                    adapter = anyAdapter
                }
            }*/
        }
    }
}