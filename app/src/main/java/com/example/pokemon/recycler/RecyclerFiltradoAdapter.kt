package com.example.pokemon.recycler

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon.databinding.RecyclerlistBinding
import com.example.pokemon.lista.Pokemons

class RecyclerFiltradoAdapter (var pokemones: MutableList<Pokemons>, var listatipos: MutableList<String>):
    RecyclerView.Adapter<RecyclerFiltradoAdapter.TextoViewHolder>() {


    class TextoViewHolder(var itemBinding: RecyclerlistBinding) :
        RecyclerView.ViewHolder(itemBinding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextoViewHolder {
        val binding =
            RecyclerlistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TextoViewHolder(binding)

    }

    override fun onBindViewHolder(holder: TextoViewHolder, pos: Int) {
        if (listatipos.contains(pokemones[pos].name)){
            holder.itemBinding.all.setBackgroundColor(Color.GREEN)
        }else{
            holder.itemBinding.all.setBackgroundColor(Color.RED)
        }
        holder.itemBinding.name.text = pokemones[pos].name
    }

    override fun getItemCount(): Int {
        return pokemones.size
    }

}