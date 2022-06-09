package com.example.pokemonapp.ui.detailFragment

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.pokemonapp.R
import com.example.pokemonapp.data.datasource.database.entities.PokemonEntity
import com.example.pokemonapp.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : AppCompatActivity() {

    lateinit var binding: FragmentDetailBinding
    val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bundle = intent.extras
        val id = bundle?.getInt(getString(R.string.key), 0)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        if (id != 0) {
            viewModel.getFromId(id!!)
            setObserver()
            binding.pokemon = PokemonEntity(0, "", "", "", "", false)
        }
    }

    private fun setObserver() {
        viewModel.onFromId.observe(this) {
            binding.pokemon = it
        }
    }
}