package com.example.pokemon.lista

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ListaPokemon2 (
    val gameIndices: List<JuegoIndex>,
    val generation: Generations,
    val id: Long,
    val moveDamageClass: Generations,
    val moves: List<Generations>,
    val name: String,
    val names: List<Nombre>,
    val pokemon: List<Pokemonn>
): Parcelable

@Parcelize
data class Generations (
    val name: String,
    val url: String
): Parcelable
@Parcelize
data class JuegoIndex (
    val gameIndex: Long,
    val generation: Generations
): Parcelable
@Parcelize
data class Nombre (
    val language: Generations,
    val name: String
): Parcelable
@Parcelize
data class Pokemonn (
    val pokemon: Generations,
    val slot: Long
): Parcelable