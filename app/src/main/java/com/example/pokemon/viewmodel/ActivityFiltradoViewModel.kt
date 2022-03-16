package com.example.pokemon.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemon.lista.ListaPokemon
import com.example.pokemon.lista.ListaPokemon2
import com.example.pokemon.lista.ListaPokemonFiltrado
import com.example.pokemon.lista.Pokemons
import com.google.gson.Gson
import kotlinx.coroutines.*
import okhttp3.*
import java.io.IOException

class ActivityFiltradoViewModel : ViewModel(){
    private val _isVisible by lazy { MediatorLiveData<Boolean>() }
    val isVisible: LiveData<Boolean>
        get() = _isVisible
    private val _responseText by lazy { MediatorLiveData<String>() }
    val responseText: LiveData<String>
        get() = _responseText

    private val _responseList by lazy { MediatorLiveData<List<Pokemons>>() }
    val responseList: LiveData<List<Pokemons>>
        get() = _responseList
    suspend fun setIsVisibleInMainThread(value: Boolean) = withContext(Dispatchers.Main) {
        _isVisible.value = value
    }

    suspend fun setResponseTextInMainThread(value: String) = withContext(Dispatchers.Main) {
        _responseText.value = value
    }

    suspend fun setResponseListInMainThread(value: List<Pokemons>) = withContext(Dispatchers.Main) {
        _responseList.value = value
    }

    var listaTipos= mutableListOf<String>()
    fun descargarPokemons(url: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val client = OkHttpClient()
                val request = Request.Builder()
                request.url(url)
                val call = client.newCall(request.build())
                call.enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        println(e.toString())
                        CoroutineScope(Dispatchers.Main).launch {
                            delay(2000)
                        }
                    }
                    override fun onResponse(call: Call, response: Response) {
                        println(response.toString())
                        response.body?.let { responseBody ->
                            val body = responseBody.string()
                            val gson = Gson()
                            val personas = gson.fromJson(body, ListaPokemonFiltrado::class.java)
                            print("HOASFD ${personas.types[0].type.url}")
                            descargarPokemonsTipo(personas.types[0].type.url)
                        }
                    }
                })
            }
        }
    }
    fun descargarPokemonsTipo(listaTipo: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                setIsVisibleInMainThread(true)
                val client = OkHttpClient()
                val request = Request.Builder()
                request.url(listaTipo)
                print(" BUENAS TARDES $listaTipo ")
                val call = client.newCall(request.build())
                call.enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        println(e.toString())
                        CoroutineScope(Dispatchers.Main).launch {
                            delay(2000)
                            setResponseTextInMainThread("Algo ha ido mal")
                            setIsVisibleInMainThread(false)
                        }
                    }
                    override fun onResponse(call: Call, response: Response) {
                        println(response.toString())
                        response.body?.let { responseBody ->
                            val body = responseBody.string()
                            println(body)
                            val gson = Gson()
                            val personas = gson.fromJson(body, ListaPokemon2::class.java)
                            println(personas)
                            CoroutineScope(Dispatchers.Main).launch {
                                delay(3000)
                                personas.pokemon.forEach {
                                    listaTipos.add(it.pokemon.name)
                                }
                                setIsVisibleInMainThread(false)
                                setResponseTextInMainThread("Hemos obtenido $personas ")
                                descargarPokemonsAll("https://pokeapi.co/api/v2/pokemon?limit=100&offset=0")
                            }
                        }
                    }
                })
            }
        }
    }
    fun descargarPokemonsAll(url: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                setIsVisibleInMainThread(true)
                val client = OkHttpClient()
                val request = Request.Builder()
                request.url(url)
                val call = client.newCall(request.build())
                call.enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        println(e.toString())
                        CoroutineScope(Dispatchers.Main).launch {
                            delay(2000)
                            setResponseTextInMainThread("Algo ha ido mal")
                            setIsVisibleInMainThread(false)
                        }
                    }
                    override fun onResponse(call: Call, response: Response) {
                        println(response.toString())
                        response.body?.let { responseBody ->
                            val body = responseBody.string()
                            println(body)
                            val gson = Gson()
                            val personas = gson.fromJson(body, ListaPokemon::class.java)
                            println(personas)
                            CoroutineScope(Dispatchers.Main).launch {
                                delay(2000)
                                setIsVisibleInMainThread(false)
                                setResponseListInMainThread(personas.results)
                                setResponseTextInMainThread("Hemos obtenido ${personas.results.count()} ")
                            }
                        }
                    }
                })
            }
        }
    }
}