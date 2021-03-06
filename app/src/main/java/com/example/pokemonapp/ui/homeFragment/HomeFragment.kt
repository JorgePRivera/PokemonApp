package com.example.pokemonapp.ui.homeFragment

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.pokemonapp.R
import com.example.pokemonapp.data.datasource.database.entities.PokemonEntity
import com.example.pokemonapp.databinding.FragmentHomeBinding
import com.example.pokemonapp.sys.util.Constats
import com.example.pokemonapp.ui.detailFragment.DetailFragment
import com.example.pokemonapp.ui.favoriteFragment.FavoriteFragment
import com.example.pokemonapp.ui.firebaseUI.homeFirebase.HomeFragmentFirebase
import com.example.pokemonapp.ui.homeFragment.adapter.PokemonListAdapter
import com.example.pokemonapp.ui.homeFragment.adapter.events.OnPokemonListener
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), OnPokemonListener {

    val viewModel: HomeViewModel by viewModels()

    lateinit var binding: FragmentHomeBinding
    private var frag: FragmentManager? = null
    private lateinit var adapter: PokemonListAdapter
    private lateinit var fra: Fragment


    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
        frag = requireActivity().supportFragmentManager
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(LayoutInflater.from(context), container, false)
        (activity as AppCompatActivity?)!!.setSupportActionBar(binding.toolbar)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getListPokemon(0, 898)
        setObserver()
        listeners()


    }

    private fun listeners() {
        binding.fabFavorite.setOnClickListener {
            if (frag!!.fragments.size <= 2) {
                fra = FavoriteFragment()
                launchEdidFragment(fra, frag!!)
                Constats.setOrigen(Constats.ORIGEN_FAVORITES)
                //Constats.ORIGEN = Constats.ORIGEN_FAVORITES
            }
        }

        binding.fabFirebase.setOnClickListener {
            if (frag!!.fragments.size <= 2) {
                fra = HomeFragmentFirebase()
                launchEdidFragment(fra, frag!!)
                Constats.setOrigen(Constats.ORIGEN_FIREBASE)
                //Constats.ORIGEN = Constats.ORIGEN_FIREBASE
            }
        }
    }


    private fun setObserver() {
        with(viewModel) {
            listpokemon.observe(viewLifecycleOwner) {
                setupRecylcerView(it)
            }

            onError.observe(viewLifecycleOwner) {
                Snackbar.make(binding.containerMain, it, Snackbar.LENGTH_LONG)
                    .show()
            }

            lifecycleScope.launchWhenStarted {
                viewModel.progress.collect {
                    binding.progress.isVisible = it
                }
            }
        }
    }

    private fun setupRecylcerView(lista: List<PokemonEntity>) {
        if (lista.isNotEmpty()) {
            adapter = PokemonListAdapter(lista, this)
            binding.adapter = adapter
        }
    }

    override fun onClick(pokemonEntity: PokemonEntity, view: View) {
        val intento = Intent(activity, DetailFragment::class.java)
        intento.putExtra(getString(R.string.key), pokemonEntity.id)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            requireActivity(),
            view,
            getString(R.string.share_element_item_home)
        )

        startActivity(intento, options.toBundle())

    }

    private fun launchEdidFragment(
        fragment: Fragment,
        fragmentManager: FragmentManager
    ) {
        val fragmentTransaction = fragmentManager.beginTransaction().setCustomAnimations(
            com.airbnb.lottie.R.anim.abc_fade_in,
            com.airbnb.lottie.R.anim.abc_fade_out,
            com.airbnb.lottie.R.anim.abc_fade_in,
            com.airbnb.lottie.R.anim.abc_fade_out
        )



        fragmentTransaction.add(R.id.fragmetContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onClickFAB(pokemonEntity: PokemonEntity) {
        pokemonEntity.isFavorite = !pokemonEntity.isFavorite
        viewModel.updatePokemon(pokemonEntity)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_searsh, menu)
        val item: MenuItem = menu.findItem(R.id.searchDataClient)
        val searchView = item.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (adapter != null) {
                    adapter.filter.filter(newText)
                }
                return true
            }
        })
    }
}