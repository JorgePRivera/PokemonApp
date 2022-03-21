package com.example.pokemonapp.data.datasource.database.dao

import androidx.room.*
import com.example.pokemonapp.data.datasource.database.entities.PokemonEntity

@Dao
interface PokemonDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertar(pokemonEntity: PokemonEntity)

    @Query("SELECT * FROM Pokemon")
    fun getAll(): List<PokemonEntity>

    @Update
    fun update(pokemonEntity: PokemonEntity)

    @Query("SELECT * FROM Pokemon WHERE id = :id")
    fun getPokemonFromId(id: Int): PokemonEntity

    @Query("SELECT * FROM Pokemon WHERE isfavorite = :isfavor")
    fun getFavorite(isfavor: Boolean): List<PokemonEntity>
}