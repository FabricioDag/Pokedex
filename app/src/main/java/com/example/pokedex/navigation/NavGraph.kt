package com.example.pokedex.navigation

import DashboardScreenView
import SplashScreenView
import androidx.compose.runtime.Composable

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pokedex.features.details.DetailsScreenView

@Composable
fun AppNavHost(navController: NavHostController){
    NavHost(navController = navController, startDestination = SplashScreen.route){
        composable(route = SplashScreen.route){
            SplashScreenView(navController)
        }

        composable(route = DashboardScreen.route){
            DashboardScreenView(navController)
        }

        composable(route = DetailsScreen.route){
            DetailsScreenView(navController, id)
        }
    }
}