package com.example.pokemonapp.ui.homeFragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import com.example.pokemonapp.R
import com.example.pokemonapp.data.datasource.database.entities.PokemonEntity
import com.example.pokemonapp.databinding.FragmentHomeBinding
import com.example.pokemonapp.sys.di.component.DaggerViewModelComponent
import com.example.pokemonapp.sys.di.module.ContextModule
import com.example.pokemonapp.sys.util.Constats
import com.example.pokemonapp.ui.detailFragment.DetailFragment
import com.example.pokemonapp.ui.favoriteFragment.FavoriteFragment
import com.example.pokemonapp.ui.homeFragment.adapter.PokemonListAdapter
import com.example.pokemonapp.ui.homeFragment.adapter.events.OnPokemonListener
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


class HomeFragment : Fragment(), OnPokemonListener {
    @Inject
    lateinit var viewModel: HomeViewModel

    lateinit var binding: FragmentHomeBinding
    private var frag: FragmentManager? = null
    private lateinit var adapter: PokemonListAdapter
    private lateinit var fra: Fragment


    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
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
                Constats.ORIGEN = Constats.ORIGEN_FAVORITES
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
                viewModel.progress.collect{
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

    override fun onClick(pokemonEntity: PokemonEntity) {
        val args = Bundle()
        args.putInt(getString(R.string.key), pokemonEntity.id)
        if (frag!!.fragments.size <= 2) {
            fra = DetailFragment()
            launchEdidFragment(args, fra, frag!!)
            Constats.ORIGEN = Constats.ORIGEN_DETAIL
        }
    }

    private fun launchEdidFragment(
        args: Bundle? = null,
        fragment: Fragment,
        fragmentManager: FragmentManager
    ) {
        if (args != null) fragment.arguments = args
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragmetContainer, fragment)
            .addToBackStack("Home")
            .commit()
    }

    private fun launchEdidFragment(
        fragment: Fragment,
        fragmentManager: FragmentManager
    ) {
        val fragmentTransaction = fragmentManager.beginTransaction()
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