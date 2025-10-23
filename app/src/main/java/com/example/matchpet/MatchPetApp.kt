package com.example.matchpet

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.matchpet.core.MatchPetApplication
import com.example.matchpet.core.di.MatchPetViewModelFactory
import com.example.matchpet.domain.model.PreferenceForm
import com.example.matchpet.feature.auth.login.LoginRoute
import com.example.matchpet.feature.auth.register.RegisterRoute
import com.example.matchpet.feature.form.PreferenceFormRoute
import com.example.matchpet.feature.home.HomeScreen
import com.example.matchpet.feature.match.MatchResultsRoute
import com.example.matchpet.feature.profile.ProfileRoute
import com.example.matchpet.navigation.AppDestination

@Composable
fun MatchPetApp() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val application = context.applicationContext as MatchPetApplication
    val factory: MatchPetViewModelFactory = remember { application.container.viewModelFactory }

    NavHost(navController = navController, startDestination = AppDestination.Home.route) {
        composable(AppDestination.Home.route) {
            HomeScreen(
                onLoginClick = { navController.navigate(AppDestination.Login.route) },
                onRegisterClick = { navController.navigate(AppDestination.Register.route) }
            )
        }
        composable(AppDestination.Login.route) {
            val loginViewModel = viewModel<com.example.matchpet.feature.auth.login.LoginViewModel>(factory = factory)
            LoginRoute(
                viewModel = loginViewModel,
                onNavigateToRegister = { navController.navigate(AppDestination.Register.route) },
                onLoginSuccess = {
                    navController.navigate(AppDestination.Form.route) {
                        popUpTo(AppDestination.Home.route) { inclusive = false }
                    }
                }
            )
        }
        composable(AppDestination.Register.route) {
            val registerViewModel = viewModel<com.example.matchpet.feature.auth.register.RegisterViewModel>(factory = factory)
            RegisterRoute(
                viewModel = registerViewModel,
                onNavigateToLogin = {
                    navController.popBackStack()
                },
                onRegistered = {
                    navController.navigate(AppDestination.Form.route) {
                        popUpTo(AppDestination.Home.route) { inclusive = false }
                    }
                }
            )
        }
        composable(AppDestination.Form.route) {
            val formViewModel = viewModel<com.example.matchpet.feature.form.PreferenceFormViewModel>(factory = factory)
            PreferenceFormRoute(
                viewModel = formViewModel,
                onFormSaved = { form ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("form", form)
                    navController.navigate(AppDestination.Matches.route)
                }
            )
        }
        composable(AppDestination.Matches.route) {
            val form = navController.previousBackStackEntry?.savedStateHandle?.get<PreferenceForm>("form")
            val matchViewModel = viewModel<com.example.matchpet.feature.match.MatchResultsViewModel>(factory = factory)
            MatchResultsRoute(viewModel = matchViewModel, form = form)
        }
        composable(AppDestination.Profile.route) {
            val profileViewModel = viewModel<com.example.matchpet.feature.profile.ProfileViewModel>(factory = factory)
            ProfileRoute(
                viewModel = profileViewModel,
                onLoggedOut = {
                    navController.navigate(AppDestination.Login.route) {
                        popUpTo(AppDestination.Home.route) { inclusive = false }
                    }
                }
            )
        }
    }
}
