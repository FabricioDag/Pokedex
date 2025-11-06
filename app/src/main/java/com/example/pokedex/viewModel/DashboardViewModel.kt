package com.example.pokedex.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.util.copy
import com.example.pokedex.domain.data.Repository
import com.example.pokedex.domain.models.Pokemon
import com.example.pokedex.domain.models.PokemonListState
import kotlinx.coroutines.launch

class DashboardViewModel: ViewModel() {

    private val repository = Repository()// trocar por hilt

    // Estado atual da tela
    var uiState by mutableStateOf(PokemonListState())
        private set

    private var currentPage = 0
    private var isLoadingMore = false

    init {
        loadPokemonList()
    }

    fun loadPokemonList() {
        // Evita múltiplas requisições simultâneas
        if (isLoadingMore || uiState.isEndReached) return

        viewModelScope.launch {
            try {
                isLoadingMore = true
                uiState = uiState.copy(isLoading = true)

                val response = repository.getPokemonList(offset = currentPage * 20)

                val newPokemons = response.body()?.results ?: emptyList()

                uiState = uiState.copy(
                    pokemons = uiState.pokemons + newPokemons,
                    isLoading = false,
                    isEndReached = newPokemons.isEmpty()
                )

                currentPage++
            } catch (e: Exception) {
                uiState = uiState.copy(isLoading = false, errorMessage = e.message)
            } finally {
                isLoadingMore = false
            }
        }
    }

    fun filterByGeneration(generationId: Int) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, selectedGeneration = generationId)
            try {
                val response = repository.getGeneration(generationId)
                if (response.isSuccessful) {
                    val speciesList = response.body()?.pokemon_species ?: emptyList()

                    // Mapear os nomes (você pode buscar detalhes depois se quiser)
                    val filteredList = speciesList.map { Pokemon(
                        name = it.name,
                        url = it.url
                    ) }

                    uiState = uiState.copy(
                        pokemons = filteredList,
                        isLoading = false
                    )
                } else {
                    uiState = uiState.copy(isLoading = false, error = response.message())
                }
            } catch (e: Exception) {
                uiState = uiState.copy(isLoading = false, error = e.message)
            }
        }
    }

    fun filterByType(typeId: Int) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, selectedType = typeId)
            try {
                val response = repository.getType(typeId)
                if (response.isSuccessful) {
                    val typeData = response.body()
                    val pokemonList = typeData?.pokemon?.map {
                        Pokemon(name = it.pokemon.name, url = it.pokemon.url)
                    } ?: emptyList()

                    uiState = uiState.copy(
                        pokemons = pokemonList,
                        isLoading = false
                    )
                } else {
                    uiState = uiState.copy(isLoading = false, error = response.message())
                }
            } catch (e: Exception) {
                uiState = uiState.copy(isLoading = false, error = e.message)
            }
        }
    }

    // 🔍 Filtro por nome
    fun filterByName(query: String) {
        uiState = uiState.copy(searchQuery = query)
    }

    // 🔥 Função derivada: retorna lista filtrada
    fun getFilteredList(): List<Pokemon> {
        val query = uiState.searchQuery.lowercase()
        return uiState.pokemons.filter { it.name.lowercase().contains(query) }
    }

    fun onSearchQueryChanged(query: String) {
        uiState = uiState.copy(searchQuery = query)
        if (query.isNotEmpty()) {
            //searchPokemonByName(query)
        } else {
            loadPokemonList()
        }
    }

//    private fun searchPokemonByName(name: String) {
//        viewModelScope.launch {
//            try {
//                uiState = uiState.copy(isLoading = true)
//                val response = repository.getPokemonByName(name.lowercase())
//                if (response.isSuccessful) {
//                    uiState = uiState.copy(
//                        pokemons = listOf(response.body()!!),
//                        isLoading = false
//                    )
//                } else {
//                    uiState = uiState.copy(error = "Pokémon not found", isLoading = false)
//                }
//            } catch (e: Exception) {
//                uiState = uiState.copy(error = e.localizedMessage, isLoading = false)
//            }
//        }
//    }


}