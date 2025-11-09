package com.example.pokedex.features.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.domain.models.PokemonDetailUiState
import com.example.pokedex.gateway.PokedexApiRepository
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val repository: PokedexApiRepository = PokedexApiRepository()
) : ViewModel() {

    var uiState by mutableStateOf(PokemonDetailUiState())
        private set

    fun loadPokemonDetail(name: String) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, error = null)

            try {
                val response = repository.getPokemonDetail(name)
                if (response.isSuccessful) {
                    uiState = uiState.copy(
                        pokemon = response.body(),
                        isLoading = false
                    )
                } else {
                    uiState = uiState.copy(
                        isLoading = false,
                        error = "Erro ao carregar Pokémon: ${response.message()}"
                    )
                }
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    error = e.message ?: "Erro desconhecido"
                )
            }
        }
    }
}