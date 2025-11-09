package com.example.pokedex.features.dashboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.domain.models.Pokemon
import com.example.pokedex.gateway.PokedexApiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DashboardViewModel: ViewModel() {

    private val repository = PokedexApiRepository()// trocar por hilt

    var uiState by mutableStateOf(DashboardUIState())

    private var isLoadingMore = false

    //
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isFilterSheetOpen = MutableStateFlow(false)
    val isFilterSheetOpen = _isFilterSheetOpen.asStateFlow()




    //CACHING
    private var allPokemonCache: List<Pokemon> = emptyList()

    init {
        loadAllPokemonList()
    }



    fun loadAllPokemonList(){
        viewModelScope.launch {
            try{
                val response = repository.getPokemonList(0,10000)


                if (response.isSuccessful){
                    val allPokemon = response.body()?.results ?: emptyList()

                    // Guarda no cache
                    allPokemonCache = allPokemon

                    uiState = uiState.copy(
                        pokemons = allPokemon,
                        isLoading = false,
                        isEndReached = allPokemon.isEmpty()
                    )
                }else {
                    uiState = uiState.copy(isLoading = false, errorMessage = response.message())
                }


            }catch (e: Exception){
                uiState = uiState.copy(isLoading = false, errorMessage = e.message)
            }finally {
                isLoadingMore = false
            }
        }
    }

    fun loadPokemonByGeneration(generationId: Int) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, selectedGeneration = generationId)
            try {
                val response = repository.getGeneration(generationId)
                if (response.isSuccessful) {
                    val speciesList = response.body()?.pokemon_species ?: emptyList()

                    val filteredList = speciesList.map {
                        Pokemon(
                            name = it.name,
                            url = it.url
                        )
                    }

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

    fun loadPokemonByType(typeId: Int) {
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

    fun filterByName(query: String) {
        val filtered = allPokemonCache.filter { it.name.contains(query, ignoreCase = true) }
        uiState = uiState.copy(pokemons = filtered)
    }

    fun getFilteredList(): List<Pokemon> {
        val query = uiState.searchQuery.lowercase()
        return uiState.pokemons.filter { it.name.lowercase().contains(query) }
    }

    fun updateSearchText(text: String) {
        _searchText.value = text
    }

    fun performSearch() {
        filterByName(searchText.value)
    }

    fun toggleFilterSheet(open: Boolean) {
        _isFilterSheetOpen.value = open
    }

    fun applyFilters(generation: String, types: List<String>) {
        // lógica de filtragem
    }




}