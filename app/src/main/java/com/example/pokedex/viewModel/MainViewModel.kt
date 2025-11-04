package com.example.pokedex.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.domain.data.Repository
import com.example.pokedex.domain.models.Data
import com.example.pokedex.domain.models.Pokemon
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    private val repository = Repository()
    var state by mutableStateOf(ScreenState())
    init{
        viewModelScope.launch{
            val response = repository.getPokemonList()
            state = state.copy(
                pokemon = response.body()!!.results
            )
        }
    }
}

data class ScreenState(
    val pokemon: List<Pokemon> = emptyList(),

    )