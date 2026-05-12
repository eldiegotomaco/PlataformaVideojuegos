package com.Diego.plataformavideojuegosmovil.data.repository

import com.Diego.plataformavideojuegosmovil.data.api.RetrofitClient
import com.Diego.plataformavideojuegosmovil.data.models.AuthResponse
import com.Diego.plataformavideojuegosmovil.data.models.BibliotecaItem
import com.Diego.plataformavideojuegosmovil.data.models.LoginRequest
import com.Diego.plataformavideojuegosmovil.data.models.MensajeResponse
import com.Diego.plataformavideojuegosmovil.data.models.RegisterRequest
import com.Diego.plataformavideojuegosmovil.data.models.Videojuego

class VideojuegoRepository {

    suspend fun login(email: String, password: String): Result<AuthResponse> {
        return try {
            val response = RetrofitClient.apiService.login(
                LoginRequest(email = email, password = password)
            )

            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Correo o contraseña incorrectos"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("No se pudo conectar con la API"))
        }
    }

    suspend fun register(nombre: String, email: String, password: String): Result<MensajeResponse> {
        return try {
            val response = RetrofitClient.apiService.register(
                RegisterRequest(nombre = nombre, email = email, password = password)
            )

            if (response.isSuccessful) {
                Result.success(response.body() ?: MensajeResponse(mensaje = "Usuario registrado"))
            } else {
                Result.failure(Exception("No se pudo registrar el usuario"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("No se pudo conectar con la API"))
        }
    }

    suspend fun obtenerVideojuegos(): Result<List<Videojuego>> {
        return try {
            val response = RetrofitClient.apiService.obtenerVideojuegos()

            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                Result.failure(Exception("Error al obtener videojuegos"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("No se pudo conectar con la API"))
        }
    }

    suspend fun obtenerVideojuegoPorId(id: Int): Result<Videojuego> {
        return try {
            val response = RetrofitClient.apiService.obtenerVideojuegoPorId(id)

            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Videojuego no encontrado"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("No se pudo conectar con la API"))
        }
    }

    suspend fun comprarVideojuego(usuarioId: Int, videojuegoId: Int): Result<MensajeResponse> {
        return try {
            val response = RetrofitClient.apiService.comprarVideojuego(
                usuarioId = usuarioId,
                videojuegoId = videojuegoId
            )

            if (response.isSuccessful) {
                Result.success(response.body() ?: MensajeResponse(mensaje = "Juego agregado a biblioteca"))
            } else {
                Result.failure(Exception("No se pudo agregar a biblioteca"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("No se pudo conectar con la API"))
        }
    }

    suspend fun obtenerBiblioteca(usuarioId: Int): Result<List<BibliotecaItem>> {
        return try {
            val response = RetrofitClient.apiService.obtenerBiblioteca(usuarioId)

            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                Result.failure(Exception("No se pudo obtener la biblioteca"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("No se pudo conectar con la API"))
        }
    }
}