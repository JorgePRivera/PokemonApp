package com.example.pokemonapp.ui.firebaseUI.homeFirebase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.example.pokemonapp.R
import com.example.pokemonapp.data.datasource.database.entities.PokemonEntityFirebase
import com.example.pokemonapp.databinding.FragmentHomeFirebaseBinding
import com.example.pokemonapp.sys.util.Constats
import com.example.pokemonapp.ui.favoriteFragment.FavoriteFragment
import com.example.pokemonapp.ui.firebaseUI.add.AddFragmentFireBase
import com.example.pokemonapp.ui.firebaseUI.homeFirebase.adapter.HomeListFirebaseAdapter
import com.example.pokemonapp.ui.firebaseUI.homeFirebase.adapter.events.HomeListFirebaseListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragmentFirebase : Fragment(), HomeListFirebaseListener {
    private val viewModel: ViewModelHomeFireBase by viewModels()
    private lateinit var binding: FragmentHomeFirebaseBinding
    private var frag: FragmentManager? = null
    private lateinit var fra: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        frag = requireActivity().supportFragmentManager
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentHomeFirebaseBinding.inflate(LayoutInflater.from(context), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getListaToFirebase()
        setObserver()
        setListener()
    }

    private fun setListener() {
        with(binding) {
            toolbar.setNavigationOnClickListener {
                requireActivity().onBackPressed()
                //Constats.ORIGEN = Constats.ORIGEN_HOME
            }
            fabAddFirebase.setOnClickListener {
                if (frag!!.fragments.size <= 3) {
                    fra = AddFragmentFireBase()
                    Constats.showFragment(fra, frag!!)
                    Constats.setOrigen(Constats.ORIGEN_ADDFIREBASE)
//                    Constats.ORIGEN = Constats.ORIGEN_ADDFIREBASE
                }
            }
        }
    }

    private fun setObserver() {
        with(viewModel) {
            listFirebase.observe(viewLifecycleOwner) {
                setupRecylcerView(it)
            }

            isvisble.observe(viewLifecycleOwner) {
                binding.progressFirebase.isVisible = it
            }
        }
    }

    private fun setupRecylcerView(lista: List<PokemonEntityFirebase>) {
        binding.adapter = HomeListFirebaseAdapter(lista, this)
    }

    override fun onClick(pokemonEntityFirebase: PokemonEntityFirebase) {
        Toast.makeText(context, "${pokemonEntityFirebase.nombre}", Toast.LENGTH_SHORT).show()
    }

    /*private fun launchEdidFragment(
        fragment: Fragment,
        fragmentManager: FragmentManager
    ) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmetContainer, fragment)
            .addToBackStack(null)
            .commit()
    }*/
}