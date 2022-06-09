package com.example.pokemonapp.data.datasource.database.entities

import android.net.Uri

data class PokemonEntityFirebase(
    var key: String,
    var nombre: String,
    var img_normal: String,
    var id_imgNormal:String,
    var img_shiny: String,
    var id_imgShiby:String
) {
    constructor() : this("", "", "", "","","")
   /* constructor(
        key: String,
        nombre: String,
        img_normal: Uri?,
        id_imgNormal: String?,
        img_shiny: Uri?,
        id_imgShiby: String?
    ) : this()*/
}
