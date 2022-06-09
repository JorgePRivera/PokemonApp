package com.example.pokemonapp.ui.detailFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.transition.TransitionInflater
import com.example.pokemonapp.R
import com.example.pokemonapp.data.datasource.database.entities.PokemonEntity
import com.example.pokemonapp.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    lateinit var binding: FragmentDetailBinding
    val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.share_elements)
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

        val heroImageView = view.findViewById<AppCompatImageView>(R.id.img_normal)
        ViewCompat.setTransitionName(heroImageView, getString(R.string.share_element_item_home))

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