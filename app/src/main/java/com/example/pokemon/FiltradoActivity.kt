package com.example.pokemon

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokemon.databinding.ActivityMainBinding
import com.example.pokemon.databinding.RecyclerlistBinding
import com.example.pokemon.lista.Pokemons
import com.example.pokemon.recycler.RecyclerFiltradoAdapter
import com.example.pokemon.viewmodel.ActivityFiltradoViewModel


var listastipo: List<Pokemons>? = null
class FiltradoActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: ActivityFiltradoViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dataPokemon= intent.getStringExtra("datapokemon")
        val url = "$dataPokemon"
        viewModel.descargarPokemons(url)
        initObserver()
    }

    private fun initObserver() {
        viewModel.isVisible.observe(this) { isVisible ->
            if (isVisible) setVisible() else setGone()
        }

        viewModel.responseText.observe(this) { responseText ->
            showToast(responseText)
        }

        viewModel.responseList.observe(this) {
            setRecycler(it as MutableList<Pokemons>)
        }
    }
    private fun setVisible() {
        binding.pbDownloading.visibility = View.VISIBLE
    }

    private fun setGone() {
        binding.pbDownloading.visibility = View.GONE
    }

    private fun showToast(text: String) {
        Toast.makeText(this@FiltradoActivity, text, Toast.LENGTH_SHORT).show()

    }

    private fun setRecycler(listas: MutableList<Pokemons>) {

        val adapter = RecyclerFiltradoAdapter(listas,viewModel.listaTipos)

        listastipo = listas
        binding.recycler.layoutManager = LinearLayoutManager(this@FiltradoActivity)
        binding.recycler.adapter = adapter
    }

}