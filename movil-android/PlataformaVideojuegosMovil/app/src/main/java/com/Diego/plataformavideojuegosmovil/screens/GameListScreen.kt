package com.Diego.plataformavideojuegosmovil.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.Diego.plataformavideojuegosmovil.data.models.Videojuego
import com.Diego.plataformavideojuegosmovil.ui.theme.NeonBackground
import com.Diego.plataformavideojuegosmovil.ui.theme.NeonCyan
import com.Diego.plataformavideojuegosmovil.ui.theme.NeonPink
import com.Diego.plataformavideojuegosmovil.ui.theme.NeonSurface
import com.Diego.plataformavideojuegosmovil.ui.theme.NeonTextPrimary
import com.Diego.plataformavideojuegosmovil.ui.theme.NeonTextSecondary
import com.Diego.plataformavideojuegosmovil.viewmodel.VideojuegoViewModel
import coil.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.layout.size

@Composable
fun GameListScreen(
    onGameClick: (Int) -> Unit,
    onBackClick: () -> Unit,
    viewModel: VideojuegoViewModel
) {
    val videojuegos by viewModel.videojuegos.collectAsState()
    val cargando by viewModel.cargando.collectAsState()
    val mensajeError by viewModel.mensajeError.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.cargarVideojuegos()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NeonBackground)
            .padding(18.dp)
    ) {
        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "NeonGames",
            style = MaterialTheme.typography.headlineLarge,
            color = NeonCyan,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Catálogo de videojuegos",
            style = MaterialTheme.typography.bodyLarge,
            color = NeonPink
        )

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
        } else if (mensajeError.isNotBlank()) {
            Text(
                text = mensajeError,
                color = MaterialTheme.colorScheme.error
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { viewModel.cargarVideojuegos() },
                colors = ButtonDefaults.buttonColors(containerColor = NeonCyan)
            ) {
                Text("Reintentar")
            }
        } else {
            LazyColumn {
                items(videojuegos) { juego ->
                    GameCard(
                        juego = juego,
                        onClick = {
                            onGameClick(juego.id)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun GameCard(
    juego: Videojuego,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = NeonSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Row {
                AsyncImage(
                    model = juego.imagenUrl,
                    contentDescription = juego.titulo ?: "Imagen del juego",
                    modifier = Modifier.size(86.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = juego.titulo ?: "Sin título",
                        style = MaterialTheme.typography.titleLarge,
                        color = NeonCyan,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = juego.descripcion ?: "Sin descripción",
                        style = MaterialTheme.typography.bodyMedium,
                        color = NeonTextPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Precio: Bs. ${juego.precio}",
                color = NeonPink,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Toca para ver detalle completo",
                style = MaterialTheme.typography.bodySmall,
                color = NeonTextSecondary
            )
        }
    }
}