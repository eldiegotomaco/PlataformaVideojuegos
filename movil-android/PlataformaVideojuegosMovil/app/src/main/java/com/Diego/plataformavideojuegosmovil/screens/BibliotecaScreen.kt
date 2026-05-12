package com.Diego.plataformavideojuegosmovil.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.Diego.plataformavideojuegosmovil.ui.theme.NeonBackground
import com.Diego.plataformavideojuegosmovil.ui.theme.NeonCyan
import com.Diego.plataformavideojuegosmovil.ui.theme.NeonPink
import com.Diego.plataformavideojuegosmovil.ui.theme.NeonSurface
import com.Diego.plataformavideojuegosmovil.ui.theme.NeonTextPrimary
import com.Diego.plataformavideojuegosmovil.ui.theme.NeonTextSecondary
import com.Diego.plataformavideojuegosmovil.viewmodel.VideojuegoViewModel

@Composable
fun BibliotecaScreen(
    viewModel: VideojuegoViewModel,
    onBackClick: () -> Unit
) {
    val biblioteca by viewModel.biblioteca.collectAsState()
    val cargando by viewModel.cargando.collectAsState()
    val mensajeError by viewModel.mensajeError.collectAsState()

    val totalGastado = biblioteca.sumOf { item ->
        item.videojuego?.precio ?: 0.0
    }

    LaunchedEffect(Unit) {
        viewModel.cargarBiblioteca()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NeonBackground)
            .padding(18.dp)
    ) {
        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "Mi biblioteca",
            style = MaterialTheme.typography.headlineLarge,
            color = NeonCyan,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Juegos comprados por el usuario",
            style = MaterialTheme.typography.bodyLarge,
            color = NeonPink
        )

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = NeonSurface)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Resumen de compras",
                    color = NeonTextSecondary,
                    style = MaterialTheme.typography.bodySmall
                )

                Text(
                    text = "Bs. %.2f gastados".format(totalGastado),
                    color = NeonCyan,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "${biblioteca.size} juego(s) en tu biblioteca",
                    color = NeonPink,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        OutlinedButton(
            onClick = onBackClick,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp)
        ) {
            Text(
                text = "← Volver al inicio",
                color = NeonCyan
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        if (cargando) {
            CircularProgressIndicator(color = NeonCyan)
        }

        if (mensajeError.isNotBlank()) {
            Text(
                text = mensajeError,
                color = MaterialTheme.colorScheme.error
            )
        }

        if (biblioteca.isEmpty() && !cargando) {
            Text(
                text = "Todavía no tienes videojuegos en tu biblioteca.",
                style = MaterialTheme.typography.bodyLarge,
                color = NeonTextPrimary
            )
        } else {
            LazyColumn {
                items(biblioteca) { item ->
                    val juego = item.videojuego

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 14.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = NeonSurface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(18.dp)
                        ) {
                            Text(
                                text = juego?.titulo ?: "Videojuego ID: ${item.videojuegoId}",
                                style = MaterialTheme.typography.titleLarge,
                                color = NeonCyan,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = juego?.descripcion ?: "Juego agregado a tu biblioteca",
                                style = MaterialTheme.typography.bodyMedium,
                                color = NeonTextPrimary
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Precio: Bs. %.2f".format(juego?.precio ?: 0.0),
                                color = NeonPink,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = "Disponible en biblioteca",
                                color = NeonTextSecondary,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }
    }
}