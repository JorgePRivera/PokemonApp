package com.example.pokemonapp.ui.favoriteFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.pokemonapp.R
import com.example.pokemonapp.data.datasource.database.entities.PokemonEntity
import com.example.pokemonapp.databinding.FragmentFavoriteBinding
import com.example.pokemonapp.sys.di.component.DaggerViewModelComponent
import com.example.pokemonapp.sys.di.module.ContextModule
import com.example.pokemonapp.sys.util.Constats
import com.example.pokemonapp.ui.favoriteFragment.adapter.FavoriteAdapter
import com.example.pokemonapp.ui.favoriteFragment.adapter.listeners.OnclickListenerFavorite
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import javax.inject.Inject

class FavoriteFragment : Fragment(), OnclickListenerFavorite {
    @Inject
    lateinit var viewModel: FavoriteViewModel

    lateinit var binding: FragmentFavoriteBinding

    private lateinit var adapter: FavoriteAdapter

    private var frag: FragmentManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerViewModelComponent.builder()
            .contextModule(ContextModule(this))
            .build()
            .inject(this)
        frag = requireActivity().supportFragmentManager
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(LayoutInflater.from(context), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getFavorite()
        setObserver()
        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
            Constats.ORIGEN = Constats.ORIGEN_HOME
        }
    }

    private fun setObserver() {
        viewModel.onPokemonFav.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.rvContainer.isVisible = true
                binding.animation.isVisible = false
                binding.tvListaVacia.isVisible = false
                setupRecylcerView(it)
            } else {
                binding.rvContainer.isVisible = false
                binding.animation.isVisible = true
                binding.tvListaVacia.isVisible = true
            }
        }
    }

    private fun setupRecylcerView(lista: List<PokemonEntity>) {
        adapter = FavoriteAdapter(lista, this)
        binding.adapter = adapter
    }

    override fun onClick(pokemonEntity: PokemonEntity) {
        showDialog(pokemonEntity)
    }

    private fun showDialog(pokemonEntity: PokemonEntity) {
        var deleteOfFavorite = false
        MaterialAlertDialogBuilder(
            requireActivity(),
            com.google.android.material.R.style.MaterialAlertDialog_MaterialComponents_Title_Icon_CenterStacked
        )
            .setTitle(R.string.dialog_confirm_title)
            .setMessage(R.string.card_message_demo_small)
            .setPositiveButton(R.string.dialog_confirm) { _, _ ->
                deleteOfFavorite = true
                pokemonEntity.isFavorite = !pokemonEntity.isFavorite
                viewModel.updatePokemon(pokemonEntity)
            }
            .setNegativeButton(R.string.dialog_cancel, null)
            .setOnDismissListener {
                if (deleteOfFavorite) {
                    viewModel.getFavorite()
                }
            }
            .show()
    }
}