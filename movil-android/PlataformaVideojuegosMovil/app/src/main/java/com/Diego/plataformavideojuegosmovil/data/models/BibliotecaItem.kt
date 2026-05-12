package com.Diego.plataformavideojuegosmovil.data.models

data class BibliotecaItem(
    val id: Int = 0,
    val usuarioId: Int = 0,
    val videojuegoId: Int = 0,
    val videojuego: Videojuego? = null,
    val fechaCompra: String? = null,
    val fechaAgregado: String? = null
)