package com.example.pokedex.features.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.pokedex.domain.data.Repository
import com.example.pokedex.domain.models.PokemonDetail
import com.example.pokedex.navigation.DashboardScreen
import com.example.pokedex.navigation.SplashScreen
import kotlinx.coroutines.delay

@Composable
fun DetailsScreenView(navController: NavHostController, name: String) {
    var pokemonDetail by remember { mutableStateOf<PokemonDetail?>(null) }
    var isLoading by remember { mutableStateOf(true) }


    LaunchedEffect(name) {
        try {
            val response = Repository().getPokemonDetail(name)
            if (response.isSuccessful) {
                pokemonDetail = response.body()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            isLoading = false
        }
    }

    if (isLoading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }else {
        pokemonDetail?.let { pokemon ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = pokemon.sprites.frontDefault,
                    contentDescription = pokemon.name,
                    modifier = Modifier.size(200.dp)
                )

                Text(
                    text = pokemon.name.replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(top = 16.dp)
                )

                Text("ID: ${pokemon.id}")
                Text("Height: ${pokemon.height}")
                Text("Weight: ${pokemon.weight}")
                //Text("Base Experience: ${pokemon.base_experience}")
            }
        } ?: Text("Pokémon not found.")
    }
}