package com.Diego.plataformavideojuegosmovil.data.api

import com.Diego.plataformavideojuegosmovil.data.models.AuthResponse
import com.Diego.plataformavideojuegosmovil.data.models.BibliotecaItem
import com.Diego.plataformavideojuegosmovil.data.models.LoginRequest
import com.Diego.plataformavideojuegosmovil.data.models.MensajeResponse
import com.Diego.plataformavideojuegosmovil.data.models.RegisterRequest
import com.Diego.plataformavideojuegosmovil.data.models.Videojuego
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("Auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<AuthResponse>

    @POST("Auth/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<MensajeResponse>

    @GET("Videojuegoes")
    suspend fun obtenerVideojuegos(): Response<List<Videojuego>>

    @GET("Videojuegoes/{id}")
    suspend fun obtenerVideojuegoPorId(
        @Path("id") id: Int
    ): Response<Videojuego>

    @POST("Compras/comprar/{usuarioId}/{videojuegoId}")
    suspend fun comprarVideojuego(
        @Path("usuarioId") usuarioId: Int,
        @Path("videojuegoId") videojuegoId: Int
    ): Response<MensajeResponse>

    @GET("Biblioteca/usuario/{usuarioId}")
    suspend fun obtenerBiblioteca(
        @Path("usuarioId") usuarioId: Int
    ): Response<List<BibliotecaItem>>
}