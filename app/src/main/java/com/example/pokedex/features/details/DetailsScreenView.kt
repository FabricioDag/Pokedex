package com.example.pokedex.features.details

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import com.example.pokedex.navigation.DashboardScreen
import com.example.pokedex.navigation.SplashScreen
import kotlinx.coroutines.delay

@Composable
fun DetailsScreenView(navController: NavHostController, id: Number) {

    // conteúdo da tela
    Text("Hello Pokedex Detail")
}