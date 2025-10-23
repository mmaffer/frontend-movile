package com.example.matchpet.navigation

sealed class AppDestination(val route: String) {
    object Home : AppDestination("home")
    object Login : AppDestination("login")
    object Register : AppDestination("register")
    object Form : AppDestination("form")
    object Matches : AppDestination("matches")
    object Profile : AppDestination("profile")
}
