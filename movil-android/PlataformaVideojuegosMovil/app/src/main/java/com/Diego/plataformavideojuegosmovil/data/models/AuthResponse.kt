package com.Diego.plataformavideojuegosmovil.data.models

data class AuthResponse(
    val token: String? = null,
    val usuarioId: Int? = null,
    val nombre: String? = null,
    val email: String? = null,
    val rol: String? = null,
    val mensaje: String? = null
)