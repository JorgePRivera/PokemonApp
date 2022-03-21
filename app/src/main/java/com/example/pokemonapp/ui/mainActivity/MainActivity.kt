package com.example.pokemonapp.ui.mainActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.pokemonapp.R
import com.example.pokemonapp.databinding.ActivityMainBinding
import com.example.pokemonapp.sys.util.Constats
import com.example.pokemonapp.ui.homeFragment.HomeFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var fraM: FragmentManager
    private lateinit var fra: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        fraM = supportFragmentManager
        if (fraM.fragments.size < 1) {
            fra = HomeFragment()
            showFragment(fra, fraM)
        }
    }

    private fun showFragment(fragment: Fragment, fragmentManager: FragmentManager) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmetContainer, fragment)
            .commit()
    }

    override fun onBackPressed() {
        when (Constats.ORIGEN) {
            Constats.ORIGEN_HOME -> {
                showDialog()
            }
            Constats.ORIGEN_FAVORITES -> {
                fra = HomeFragment()
                showFragment(fra, fraM)
                Constats.ORIGEN = Constats.ORIGEN_HOME
            }
            Constats.ORIGEN_DETAIL -> {
                Constats.ORIGEN = Constats.ORIGEN_HOME
                super.onBackPressed()
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