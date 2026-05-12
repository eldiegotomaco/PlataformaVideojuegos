package com.Diego.plataformavideojuegosmovil.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.Diego.plataformavideojuegosmovil.ui.theme.NeonBackground
import com.Diego.plataformavideojuegosmovil.ui.theme.NeonCyan
import com.Diego.plataformavideojuegosmovil.ui.theme.NeonPink
import com.Diego.plataformavideojuegosmovil.ui.theme.NeonSurface
import com.Diego.plataformavideojuegosmovil.ui.theme.NeonTextPrimary
import com.Diego.plataformavideojuegosmovil.ui.theme.NeonTextSecondary
import com.Diego.plataformavideojuegosmovil.viewmodel.VideojuegoViewModel

@Composable
fun LoginScreen(
    viewModel: VideojuegoViewModel,
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var mensajeLocal by remember { mutableStateOf("") }

    val cargando by viewModel.cargando.collectAsState()
    val mensajeError by viewModel.mensajeError.collectAsState()
    val mensajeExito by viewModel.mensajeExito.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(NeonBackground)
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(
                containerColor = NeonSurface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 14.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "🎮 NeonGames",
                    style = MaterialTheme.typography.headlineLarge,
                    color = NeonCyan
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Ingresa con tu cuenta para acceder.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = NeonTextPrimary
                )

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        mensajeLocal = ""
                        viewModel.limpiarMensajes()
                    },
                    label = { Text("Correo electrónico") },
                    placeholder = { Text("ejemplo@gmail.com") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = NeonTextPrimary,
                        unfocusedTextColor = NeonTextPrimary,
                        focusedLabelColor = NeonCyan,
                        unfocusedLabelColor = NeonTextSecondary,
                        focusedBorderColor = NeonCyan,
                        unfocusedBorderColor = NeonTextSecondary,
                        cursorColor = NeonCyan,
                        focusedPlaceholderColor = NeonTextSecondary,
                        unfocusedPlaceholderColor = NeonTextSecondary
                    )
                )

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        mensajeLocal = ""
                        viewModel.limpiarMensajes()
                    },
                    label = { Text("Contraseña") },
                    placeholder = { Text("Mínimo 4 caracteres") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = NeonTextPrimary,
                        unfocusedTextColor = NeonTextPrimary,
                        focusedLabelColor = NeonCyan,
                        unfocusedLabelColor = NeonTextSecondary,
                        focusedBorderColor = NeonCyan,
                        unfocusedBorderColor = NeonTextSecondary,
                        cursorColor = NeonCyan,
                        focusedPlaceholderColor = NeonTextSecondary,
                        unfocusedPlaceholderColor = NeonTextSecondary
                    )
                )

                Spacer(modifier = Modifier.height(22.dp))

                if (cargando) {
                    CircularProgressIndicator(
                        color = NeonCyan
                    )
                } else {
                    Button(
                        onClick = {
                            mensajeLocal = ""
                            viewModel.limpiarMensajes()

                            when {
                                email.isBlank() || password.isBlank() -> {
                                    mensajeLocal = "Completa el correo y la contraseña."
                                }

                                !email.contains("@") || !email.contains(".") -> {
                                    mensajeLocal = "Ingresa un correo válido. Ejemplo: usuario@gmail.com"
                                }

                                password.length < 4 -> {
                                    mensajeLocal = "La contraseña debe tener mínimo 4 caracteres."
                                }

                                else -> {
                                    viewModel.login(email, password) {
                                        onLoginSuccess()
                                    }
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = NeonCyan,
                            contentColor = NeonBackground
                        )
                    ) {
                        Text("Ingresar")
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedButton(
                    onClick = {
                        viewModel.limpiarMensajes()
                        onRegisterClick()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp)
                ) {
                    Text(
                        text = "Crear cuenta nueva",
                        color = NeonCyan
                    )
                }

                if (mensajeLocal.isNotBlank()) {
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = mensajeLocal,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                if (mensajeError.isNotBlank()) {
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = mensajeError,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                if (mensajeExito.isNotBlank()) {
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = mensajeExito,
                        color = NeonPink,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "La mejor Plataforma de videojuegos para tu celular.",
                    color = NeonTextSecondary,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}