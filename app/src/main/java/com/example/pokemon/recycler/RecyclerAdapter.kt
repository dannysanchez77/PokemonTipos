package com.example.pokemon.recycler

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon.FiltradoActivity
import com.example.pokemon.databinding.RecyclerlistBinding
import com.example.pokemon.lista.Pokemons

class RecyclerAdapter(var poke: MutableList<Pokemons>) :
    RecyclerView.Adapter<RecyclerAdapter.TextoViewHolder>() {


    class TextoViewHolder(var itemBinding: RecyclerlistBinding) :
        RecyclerView.ViewHolder(itemBinding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextoViewHolder {
        val binding =
            RecyclerlistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TextoViewHolder(binding)

    }

    override fun onBindViewHolder(holder: TextoViewHolder, pos: Int) {

        holder.itemBinding.name.text = poke[pos].name
        holder.itemBinding.all.setOnClickListener {
            val intent = Intent(holder.itemBinding.root.context, FiltradoActivity::class.java)
            intent.putExtra("datapokemon", poke[pos].url)
            holder.itemBinding.root.context.startActivity(intent)


        }
    }

    override fun getItemCount(): Int {
        return poke.size
    }

}