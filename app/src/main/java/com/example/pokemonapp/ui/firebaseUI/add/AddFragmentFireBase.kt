package com.example.pokemonapp.ui.firebaseUI.add

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.pokemonapp.R
import com.example.pokemonapp.data.datasource.database.entities.PokemonEntityFirebase
import com.example.pokemonapp.databinding.FragmentAddFireBaseBinding
import com.example.pokemonapp.sys.util.Constats
import com.example.pokemonapp.sys.util.inOnline
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddFragmentFireBase : Fragment() {
    private lateinit var binding: FragmentAddFireBaseBinding
    var imageUrl_local: Uri? = null
    var imageUrl_shiny_local: Uri? = null

    val viewModel: AddViewModelFirebase by viewModels()
    private var opcion = 0

    var uriNormal: Uri? = null
    var photoNormal: String? = null
    var urishiny: Uri? = null
    var photoshiny: String? = null

    private val responsLaunCher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                if (it.data != null) {
                    if (opcion == 0) {
                        imageUrl_local = it.data?.data
                        viewModel.loadphoto(imageUrl_local.toString(), opcion)
                    } else {
                        imageUrl_shiny_local = it.data?.data
                        viewModel.loadphoto(imageUrl_shiny_local.toString(), opcion)
                    }
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentAddFireBaseBinding.inflate(LayoutInflater.from(context), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
        setActions()
        setObserver()
    }

    private fun setListener() {
        with(binding) {
            toolbarAdd.setNavigationOnClickListener {
                requireActivity().onBackPressed()
            }

            btnGuardar.setOnClickListener {
                if (validateField()) {
                    viewModel.insertDataBaseToFiserBase(
                        createPokemon()
                    )
                }
            }
        }
    }

    private fun setActions() {
        with(binding) {
            btnImgPhoto.setOnClickListener {
                opcion = 0
                fromGalery()
            }
            btnImgPhotoShiny.setOnClickListener {
                opcion = 1
                fromGalery()
            }
        }
    }

    private fun fromGalery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        responsLaunCher.launch(intent)
    }

    private fun setObserver() {
        with(viewModel) {
            photoUri.observe(viewLifecycleOwner) {
                binding.photoUri = viewModel
                viewModel.insertStoregeToFireBase(imageUrl_local!!)
            }

            photoUriShiny.observe(viewLifecycleOwner) {
                binding.photoUri = viewModel
                viewModel.insertStoregeToFireBase(imageUrl_shiny_local!!)
            }

            uriPhoto.observe(viewLifecycleOwner) { uri ->
                photoUriFb.observe(viewLifecycleOwner) { photo ->
                    if (opcion == 0) {
                        uriNormal = uri
                        photoNormal = photo
                    } else {
                        urishiny = uri
                        photoshiny = photo
                    }
                }
            }


            /**     inserta los datos en la bd      */
            insert.observe(viewLifecycleOwner) {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                with(binding) {
                    tvName.setText("")
                    viewPhoto.visibility = View.INVISIBLE
                    viewPhotoShiny.visibility = View.INVISIBLE
                    titleMensaje.setText(R.string.upload_data)
                    progressBar.progress = 0
                    titleProgress.text = ""
                }
            }

            isVisibleProgress.observe(viewLifecycleOwner) {
                //binding.containerAddFirebase.postDelayed({
                binding.progressBarInsert.isVisible = it
                //binding.titleMensaje.isVisible = it
                //}, 5000)
            }

            progress_string.observe(viewLifecycleOwner) {
                binding.titleProgress.text = it
            }

            progress_double.observe(viewLifecycleOwner) {
                if (inOnline.checkNetwork(context)) {
                    val progress_message = (String.format("%s%%", it))
                    binding.progressBar.progress = it.toInt()
                    binding.titleMensaje.text = progress_message
                } else {
                    Toast.makeText(context, "No tienes conexion a internet", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            disableUI.observe(viewLifecycleOwner) {
                with(binding) {
                    btnImgPhoto.isEnabled = it
                    btnImgPhotoShiny.isEnabled = it
                    btnGuardar.isEnabled = it
                    tvName.isEnabled = it
                    if (!it) {
                        Constats.setOrigen(Constats.IN_PROGRESS)
                        toolbarAdd.setNavigationOnClickListener {
                            Toast.makeText(
                                context,
                                "Se esta ejecutando una operación",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Constats.setOrigen(Constats.ORIGEN_ADDFIREBASE)
                        toolbarAdd.setNavigationOnClickListener {
                            requireActivity().onBackPressed()
                        }
                    }
                }
            }
        }
    }

    private fun createPokemon(): PokemonEntityFirebase {
        return PokemonEntityFirebase(
            "",
            binding.tvName.text.toString(),
            uriNormal.toString(),
            photoNormal.toString(),
            urishiny.toString(),
            photoshiny.toString()
        )
    }


    private fun validateField(): Boolean {
        var bandel = true
        if (binding.tvName.text!!.isNotEmpty()) {
            binding.tilName.error = null
        } else {
            binding.tilName.error = "Campo Requerido"
            bandel = false
        }
        if (!binding.viewPhoto.isVisible && !binding.viewPhotoShiny.isVisible) {
            Toast.makeText(context, "No has seleccionando una Imagen", Toast.LENGTH_SHORT).show()
            bandel = false
        }
        return bandel
    }
}