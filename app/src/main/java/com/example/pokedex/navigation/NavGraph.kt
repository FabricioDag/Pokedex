package com.example.pokedex.navigation

import DashboardScreenView
import SplashScreenView
import android.R.attr.type
import androidx.compose.runtime.Composable

import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
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

        //composable(route = DetailsScreen.route){
           // DetailsScreenView(navController, name)
        //}

        composable(
            route = "details/{name}",
            arguments = listOf(navArgument("name") { type = NavType.StringType })
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name")
            DetailsScreenView(navController,name ?: "" )
        }
    }
}