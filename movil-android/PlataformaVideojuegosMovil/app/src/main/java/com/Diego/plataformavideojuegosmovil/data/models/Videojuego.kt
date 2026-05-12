package com.Diego.plataformavideojuegosmovil.data.models

data class Videojuego(
    val id: Int,
    val titulo: String?,
    val descripcion: String?,
    val precio: Double,
    val imagenUrl: String?,
    val categoriaId: Int
)