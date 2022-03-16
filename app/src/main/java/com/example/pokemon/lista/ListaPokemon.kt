package com.example.pokemon.lista

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListaPokemon (
    val count: Long,
    val next: String,
    val results: List<Pokemons>
) : Parcelable

@Parcelize
data class Pokemons (
    val name: String,
    val url: String
): Parcelable