package com.Diego.plataformavideojuegosmovil.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
fun HomeScreen(
    viewModel: VideojuegoViewModel,
    onCatalogoClick: () -> Unit,
    onBibliotecaClick: () -> Unit,
    onCerrarSesionClick: () -> Unit
) {
    val nombreUsuario by viewModel.nombreUsuario.collectAsState()

    val nombreBonito = if (nombreUsuario.contains("@")) {
        nombreUsuario.substringBefore("@")
    } else {
        nombreUsuario.ifBlank { "Gamer" }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NeonBackground)
            .padding(22.dp)
    ) {
        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "NeonGames",
            style = MaterialTheme.typography.headlineLarge,
            color = NeonCyan,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Plataforma móvil de videojuegos",
            style = MaterialTheme.typography.bodyMedium,
            color = NeonPink
        )

        Spacer(modifier = Modifier.height(36.dp))

        Text(
            text = "Bienvenido,",
            style = MaterialTheme.typography.titleLarge,
            color = NeonTextSecondary
        )

        Text(
            text = nombreBonito,
            style = MaterialTheme.typography.headlineMedium,
            color = NeonCyan,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Explora videojuegos, revisa detalles y administra tu biblioteca personal.",
            style = MaterialTheme.typography.bodyMedium,
            color = NeonTextPrimary
        )

        Spacer(modifier = Modifier.height(28.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(30.dp),
            colors = CardDefaults.cardColors(containerColor = NeonSurface),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
        ) {
            Column(
                modifier = Modifier.padding(22.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Panel principal",
                    style = MaterialTheme.typography.titleLarge,
                    color = NeonPink,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Selecciona una opción para continuar.",
                    style = MaterialTheme.typography.bodySmall,
                    color = NeonTextSecondary
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = onCatalogoClick,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = NeonCyan,
                        contentColor = NeonBackground
                    )
                ) {
                    Text("🎮 Ver catálogo")
                }

                Spacer(modifier = Modifier.height(14.dp))

                Button(
                    onClick = onBibliotecaClick,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = NeonPink,
                        contentColor = NeonBackground
                    )
                ) {
                    Text("📚 Mi biblioteca")
                }

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedButton(
                    onClick = onCerrarSesionClick,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(
                        text = "Cerrar sesión",
                        color = NeonTextPrimary
                    )
                }
            }
        }
    }
}