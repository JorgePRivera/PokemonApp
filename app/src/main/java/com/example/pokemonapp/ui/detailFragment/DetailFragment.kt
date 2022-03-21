package com.example.pokemonapp.ui.detailFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pokemonapp.R
import com.example.pokemonapp.data.datasource.database.entities.PokemonEntity
import com.example.pokemonapp.databinding.FragmentDetailBinding
import com.example.pokemonapp.sys.di.component.DaggerViewModelComponent
import com.example.pokemonapp.sys.di.module.ContextModule
import com.example.pokemonapp.sys.util.Constats
import javax.inject.Inject


class DetailFragment : Fragment() {

    @Inject
    lateinit var viewModel: DetailViewModel
    lateinit var binding: FragmentDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerViewModelComponent.builder()
            .contextModule(ContextModule(this))
            .build()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(LayoutInflater.from(context), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getInt(getString(R.string.key), 0)
        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
        if (id != null && id != 0) {
            viewModel.getFromId(id)
            setObserver()
            binding.pokemon = PokemonEntity(0, "", "", "", "", false)
        }
    }

    private fun setObserver() {
        viewModel.onFromId.observe(viewLifecycleOwner) {
            binding.pokemon = it
        }
    }
}