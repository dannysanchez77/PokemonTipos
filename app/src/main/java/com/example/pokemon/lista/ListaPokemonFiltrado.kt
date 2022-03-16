package com.example.pokemon.lista

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListaPokemonFiltrado (
    val types: List<Type>,
    val weight: Long
):Parcelable



@Parcelize
data class Species (
    val name: String,
    val url: String
):Parcelable



@Parcelize
data class Type (
    val slot: Long,
    val type: Species
): Parcelable
