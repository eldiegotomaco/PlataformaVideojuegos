package com.Diego.plataformavideojuegosmovil.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Diego.plataformavideojuegosmovil.data.models.BibliotecaItem
import com.Diego.plataformavideojuegosmovil.data.models.Videojuego
import com.Diego.plataformavideojuegosmovil.data.repository.VideojuegoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VideojuegoViewModel : ViewModel() {

    private val repository = VideojuegoRepository()

    private val _usuarioId = MutableStateFlow<Int?>(null)
    val usuarioId: StateFlow<Int?> = _usuarioId

    private val _nombreUsuario = MutableStateFlow("")
    val nombreUsuario: StateFlow<String> = _nombreUsuario

    private val _videojuegos = MutableStateFlow<List<Videojuego>>(emptyList())
    val videojuegos: StateFlow<List<Videojuego>> = _videojuegos

    private val _videojuegoSeleccionado = MutableStateFlow<Videojuego?>(null)
    val videojuegoSeleccionado: StateFlow<Videojuego?> = _videojuegoSeleccionado

    private val _biblioteca = MutableStateFlow<List<BibliotecaItem>>(emptyList())
    val biblioteca: StateFlow<List<BibliotecaItem>> = _biblioteca

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando

    private val _mensajeError = MutableStateFlow("")
    val mensajeError: StateFlow<String> = _mensajeError

    private val _mensajeExito = MutableStateFlow("")
    val mensajeExito: StateFlow<String> = _mensajeExito

    fun limpiarMensajes() {
        _mensajeError.value = ""
        _mensajeExito.value = ""
    }

    fun login(email: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _cargando.value = true
            limpiarMensajes()

            val resultado = repository.login(email, password)

            resultado.onSuccess { auth ->
                if (auth.usuarioId != null) {
                    _usuarioId.value = auth.usuarioId
                    _nombreUsuario.value = auth.nombre ?: "Usuario"
                    _mensajeExito.value = "Bienvenido ${auth.nombre ?: ""}"
                    onSuccess()
                } else {
                    _mensajeError.value = "La API no devolvió usuarioId"
                }
            }.onFailure {
                _mensajeError.value = it.message ?: "Error al iniciar sesión"
            }

            _cargando.value = false
        }
    }

    fun register(nombre: String, email: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _cargando.value = true
            limpiarMensajes()

            val resultado = repository.register(nombre, email, password)

            resultado.onSuccess {
                _mensajeExito.value = it.mensaje ?: it.message ?: "Usuario registrado correctamente"
                onSuccess()
            }.onFailure {
                _mensajeError.value = it.message ?: "Error al registrar usuario"
            }

            _cargando.value = false
        }
    }

    fun cerrarSesion() {
        _usuarioId.value = null
        _nombreUsuario.value = ""
        _biblioteca.value = emptyList()
        _videojuegoSeleccionado.value = null
        limpiarMensajes()
    }

    fun cargarVideojuegos() {
        viewModelScope.launch {
            _cargando.value = true
            limpiarMensajes()

            val resultado = repository.obtenerVideojuegos()

            resultado.onSuccess {
                _videojuegos.value = it
            }.onFailure {
                _mensajeError.value = it.message ?: "No se pudo cargar videojuegos"
            }

            _cargando.value = false
        }
    }

    fun cargarVideojuegoPorId(id: Int) {
        viewModelScope.launch {
            _cargando.value = true
            limpiarMensajes()

            val resultado = repository.obtenerVideojuegoPorId(id)

            resultado.onSuccess {
                _videojuegoSeleccionado.value = it
            }.onFailure {
                _mensajeError.value = it.message ?: "No se encontró el videojuego"
            }

            _cargando.value = false
        }
    }

    fun comprarVideojuego(videojuegoId: Int) {
        val idUsuario = _usuarioId.value

        if (idUsuario == null) {
            _mensajeError.value = "Primero debes iniciar sesión"
            return
        }

        viewModelScope.launch {
            _cargando.value = true
            limpiarMensajes()

            val resultado = repository.comprarVideojuego(idUsuario, videojuegoId)

            resultado.onSuccess {
                _mensajeExito.value = it.mensaje ?: it.message ?: "Juego agregado a biblioteca"
                cargarBiblioteca()
            }.onFailure {
                _mensajeError.value = it.message ?: "No se pudo agregar a biblioteca"
            }

            _cargando.value = false
        }
    }

    fun cargarBiblioteca() {
        val idUsuario = _usuarioId.value

        if (idUsuario == null) {
            _mensajeError.value = "Primero debes iniciar sesión"
            return
        }

        viewModelScope.launch {
            _cargando.value = true
            limpiarMensajes()

            val resultado = repository.obtenerBiblioteca(idUsuario)

            resultado.onSuccess {
                _biblioteca.value = it
            }.onFailure {
                _mensajeError.value = it.message ?: "No se pudo cargar biblioteca"
            }

            _cargando.value = false
        }
    }
}