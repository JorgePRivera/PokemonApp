package com.example.pokemonapp.data.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pokemonapp.data.datasource.database.dao.PokemonDAO
import com.example.pokemonapp.data.datasource.database.entities.PokemonEntity


@Database(
    entities = [PokemonEntity::class],
    version = 1,
    exportSchema = true
)
abstract class PokemonDataBase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDAO
}