package com.autopartsx.autopartsapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.autopartsx.autopartsapp.ui.screens.*
import com.autopartsx.autopartsapp.viewmodel.AuthViewModel
import com.autopartsx.autopartsapp.viewmodel.CartViewModel
import com.autopartsx.autopartsapp.viewmodel.PartViewModel

@Composable
fun AppNavHost(
    authViewModel: AuthViewModel,
    partViewModel: PartViewModel,
    cartViewModel: CartViewModel
) {
    val navController = rememberNavController()
    val navBackEntry by navController.currentBackStackEntryAsState()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("parts") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onGoRegister = { navController.navigate("register") },
                authViewModel = authViewModel
            )
        }

        composable("register") {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate("parts") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onBackLogin = { navController.popBackStack() },
                authViewModel = authViewModel
            )
        }

        composable("parts") {
            PartListScreen(
                authViewModel = authViewModel,
                partViewModel = partViewModel,
                cartViewModel = cartViewModel,
                onAddPart = { navController.navigate("partForm/-1") },
                onEditPart = { partId ->
                    navController.navigate("partForm/$partId")
                },
                onGoCart = { navController.navigate("cart") },
                onLogout = {
                    authViewModel.logout()
                    navController.navigate("login") {
                        popUpTo("parts") { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = "partForm/{partId}",
            arguments = listOf(
                navArgument("partId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("partId") ?: -1
            PartFormScreen(
                partId = id,
                partViewModel = partViewModel,
                onSaved = {
                    navController.popBackStack()
                }
            )
        }

        composable("cart") {
            CartScreen(
                cartViewModel = cartViewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
