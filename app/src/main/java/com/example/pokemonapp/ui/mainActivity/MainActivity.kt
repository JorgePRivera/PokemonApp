package com.example.pokemonapp.ui.mainActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.pokemonapp.R
import com.example.pokemonapp.databinding.ActivityMainBinding
import com.example.pokemonapp.sys.util.Constats
import com.example.pokemonapp.ui.homeFragment.HomeFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var fraM: FragmentManager
    private lateinit var fra: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        fraM = supportFragmentManager
        if (fraM.fragments.size < 1) {
            fra = HomeFragment()
            Constats.showFragment(fra, fraM)
        }
    }

    override fun onBackPressed() {
        when (Constats.getOrigen()) {
            Constats.ORIGEN_HOME -> {
                showDialog()
            }
            Constats.ORIGEN_FAVORITES -> {
                fra = HomeFragment()
                Constats.showFragment(fra, fraM)
                Constats.setOrigen(Constats.ORIGEN_HOME)
            }
            Constats.ORIGEN_DETAIL,
            Constats.ORIGEN_FIREBASE -> {
                Constats.setOrigen(Constats.ORIGEN_HOME)
                super.onBackPressed()
            }
            Constats.ORIGEN_ADDFIREBASE -> {
                Constats.setOrigen(Constats.ORIGEN_FIREBASE)
                super.onBackPressed()
            }
            Constats.IN_PROGRESS -> {
                Toast.makeText(this, "Se esta ejecutando una operación", Toast.LENGTH_SHORT).show()
            }
            else -> {
                showDialog()
            }
        }
    }

    private fun showDialog() {
        MaterialAlertDialogBuilder(
            this,
            com.google.android.material.R.style.MaterialAlertDialog_MaterialComponents_Title_Icon_CenterStacked
        )
            .setTitle(R.string.dialog_exit_title)
            .setMessage(R.string.exit)
            .setPositiveButton(R.string.dialog_confirm) { _, _ ->
                finish()
            }
            .setNegativeButton(R.string.dialog_cancel, null)
            .show()
    }
}