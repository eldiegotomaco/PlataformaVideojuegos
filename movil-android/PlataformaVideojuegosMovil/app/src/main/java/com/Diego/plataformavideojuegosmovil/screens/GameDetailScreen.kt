package com.Diego.plataformavideojuegosmovil.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.Diego.plataformavideojuegosmovil.ui.theme.NeonBackground
import com.Diego.plataformavideojuegosmovil.ui.theme.NeonCyan
import com.Diego.plataformavideojuegosmovil.ui.theme.NeonPink
import com.Diego.plataformavideojuegosmovil.ui.theme.NeonSurface
import com.Diego.plataformavideojuegosmovil.ui.theme.NeonTextPrimary
import com.Diego.plataformavideojuegosmovil.ui.theme.NeonTextSecondary
import com.Diego.plataformavideojuegosmovil.viewmodel.VideojuegoViewModel
import kotlinx.coroutines.delay

@Composable
fun GameDetailScreen(
    id: Int,
    viewModel: VideojuegoViewModel,
    onBackClick: () -> Unit
) {
    val videojuego by viewModel.videojuegoSeleccionado.collectAsState()
    val cargando by viewModel.cargando.collectAsState()
    val mensajeError by viewModel.mensajeError.collectAsState()
    val mensajeExito by viewModel.mensajeExito.collectAsState()

    var mostrarDialogo by remember { mutableStateOf(false) }
    var animarCompra by remember { mutableStateOf(false) }

    val escalaCompra by animateFloatAsState(
        targetValue = if (animarCompra) 1.08f else 1f,
        label = "animacionCompra"
    )

    LaunchedEffect(id) {
        viewModel.cargarVideojuegoPorId(id)
    }

    LaunchedEffect(mensajeExito) {
        if (mensajeExito.isNotBlank()) {
            animarCompra = true
            delay(350)
            animarCompra = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NeonBackground)
            .padding(20.dp)
    ) {
        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "Detalle del juego",
            style = MaterialTheme.typography.headlineLarge,
            color = NeonCyan,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Información completa antes de comprar",
            style = MaterialTheme.typography.bodyMedium,
            color = NeonPink
        )

        Spacer(modifier = Modifier.height(14.dp))

        OutlinedButton(
            onClick = onBackClick,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp)
        ) {
            Text("← Volver al catálogo", color = NeonCyan)
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
            Spacer(modifier = Modifier.height(12.dp))
        }

        AnimatedVisibility(visible = mensajeExito.isNotBlank()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
                    .scale(escalaCompra),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = NeonPink)
            ) {
                Text(
                    text = "✅ $mensajeExito",
                    modifier = Modifier.padding(16.dp),
                    color = NeonBackground,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        if (videojuego != null) {
            val juego = videojuego!!

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .scale(escalaCompra),
                shape = RoundedCornerShape(30.dp),
                colors = CardDefaults.cardColors(containerColor = NeonSurface),
                elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(22.dp)
                ) {

                    AsyncImage(
                        model = juego.imagenUrl,
                        contentDescription = juego.titulo ?: "Imagen del juego",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Text(
                        text = juego.titulo ?: "Sin título",
                        style = MaterialTheme.typography.headlineMedium,
                        color = NeonCyan,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = juego.descripcion ?: "Sin descripción disponible.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = NeonTextPrimary
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Text(
                        text = "Precio",
                        color = NeonTextSecondary,
                        style = MaterialTheme.typography.bodySmall
                    )

                    Text(
                        text = "Bs. ${juego.precio}",
                        color = NeonPink,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Categoría ID: ${juego.categoriaId}",
                        color = NeonTextSecondary,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            mostrarDialogo = true
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = NeonCyan,
                            contentColor = NeonBackground
                        )
                    ) {
                        Text("Comprar y agregar a biblioteca")
                    }
                }
            }

            if (mostrarDialogo) {
                AlertDialog(
                    onDismissRequest = {
                        mostrarDialogo = false
                    },
                    title = {
                        Text(
                            text = "Confirmar compra",
                            color = NeonCyan,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    text = {
                        Text(
                            text = "¿Estás seguro de comprar ${juego.titulo ?: "este juego"} por Bs. ${juego.precio}?",
                            color = NeonTextPrimary
                        )
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                mostrarDialogo = false
                                viewModel.comprarVideojuego(juego.id)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = NeonCyan,
                                contentColor = NeonBackground
                            )
                        ) {
                            Text("Confirmar")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                mostrarDialogo = false
                            }
                        ) {
                            Text("Cancelar", color = NeonPink)
                        }
                    },
                    containerColor = NeonSurface
                )
            }
        }
    }
}