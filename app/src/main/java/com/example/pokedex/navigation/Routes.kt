package com.example.pokedex.navigation

import kotlinx.serialization.Serializable

@Serializable
data object SplashScreen: Screen("splash")

@Serializable
data object LoginScreen: Screen("login")

@Serializable
data object DashboardScreen: Screen("dashboard")

@Serializable
data object DetailsScreen: Screen("details")


sealed class  Screen(val route:String)