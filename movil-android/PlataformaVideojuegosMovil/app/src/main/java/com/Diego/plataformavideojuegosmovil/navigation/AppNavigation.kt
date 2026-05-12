package com.Diego.plataformavideojuegosmovil.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.Diego.plataformavideojuegosmovil.screens.BibliotecaScreen
import com.Diego.plataformavideojuegosmovil.screens.GameDetailScreen
import com.Diego.plataformavideojuegosmovil.screens.GameListScreen
import com.Diego.plataformavideojuegosmovil.screens.HomeScreen
import com.Diego.plataformavideojuegosmovil.screens.LoginScreen
import com.Diego.plataformavideojuegosmovil.screens.RegisterScreen
import com.Diego.plataformavideojuegosmovil.viewmodel.VideojuegoViewModel

@Composable
fun AppNavigation() {
    var pantallaActual by remember { mutableStateOf("login") }
    var videojuegoIdSeleccionado by remember { mutableIntStateOf(0) }

    val videojuegoViewModel: VideojuegoViewModel = viewModel()

    BackHandler(enabled = pantallaActual != "login") {
        pantallaActual = when (pantallaActual) {
            "register" -> "login"
            "home" -> "login"
            "catalogo" -> "home"
            "detalle" -> "catalogo"
            "biblioteca" -> "home"
            else -> "login"
        }
    }

    when (pantallaActual) {
        "login" -> {
            LoginScreen(
                viewModel = videojuegoViewModel,
                onLoginSuccess = {
                    pantallaActual = "home"
                },
                onRegisterClick = {
                    pantallaActual = "register"
                }
            )
        }

        "register" -> {
            RegisterScreen(
                viewModel = videojuegoViewModel,
                onRegisterSuccess = {
                    pantallaActual = "login"
                },
                onBackToLogin = {
                    pantallaActual = "login"
                }
            )
        }

        "home" -> {
            HomeScreen(
                viewModel = videojuegoViewModel,
                onCatalogoClick = {
                    pantallaActual = "catalogo"
                },
                onBibliotecaClick = {
                    videojuegoViewModel.cargarBiblioteca()
                    pantallaActual = "biblioteca"
                },
                onCerrarSesionClick = {
                    videojuegoViewModel.cerrarSesion()
                    pantallaActual = "login"
                }
            )
        }

        "catalogo" -> {
            GameListScreen(
                onGameClick = { id ->
                    videojuegoIdSeleccionado = id
                    pantallaActual = "detalle"
                },
                onBackClick = {
                    pantallaActual = "home"
                },
                viewModel = videojuegoViewModel
            )
        }

        "detalle" -> {
            GameDetailScreen(
                id = videojuegoIdSeleccionado,
                viewModel = videojuegoViewModel,
                onBackClick = {
                    pantallaActual = "catalogo"
                }
            )
        }

        "biblioteca" -> {
            BibliotecaScreen(
                viewModel = videojuegoViewModel,
                onBackClick = {
                    pantallaActual = "home"
                }
            )
        }
    }
}