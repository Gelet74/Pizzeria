package com.example.pizzeria.ui.ViewModel

import androidx.lifecycle.ViewModel
import com.example.pizzeria.modelo.PizzeriaUIState
import com.example.pizzeria.modelo.Precios
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PizzeriaViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PizzeriaUIState())
    val uiState: StateFlow<PizzeriaUIState> = _uiState.asStateFlow()

    fun seleccionarPizza(nombrePizza: String) {
        _uiState.update { current ->
            current.copy(
                pizzaSeleccionada = nombrePizza,
                cantidadPizza = 1
            )
        }
        calcularPrecioTotal()
    }

    fun seleccionarTamano(tamano: String) {
        _uiState.update { current ->
            current.copy(tamanoSeleccionado = tamano)
        }
        calcularPrecioTotal()
    }

    fun seleccionarBebida(nombreBebida: String) {
        _uiState.update { current ->
            current.copy(
                bebidaSeleccionada = nombreBebida,
                cantidadBebida = if (nombreBebida == "Sin bebida") 0 else 1
            )
        }
        calcularPrecioTotal()
    }

    fun cambiarCantidadPizza(delta: Int) {
        _uiState.update { current ->
            val nuevaCantidad = (current.cantidadPizza + delta).coerceAtLeast(1)
            current.copy(cantidadPizza = nuevaCantidad)
        }
        calcularPrecioTotal()
    }

    fun cambiarCantidadBebida(delta: Int) {
        _uiState.update { current ->
            val nuevaCantidad = (current.cantidadBebida + delta).coerceAtLeast(0)
            current.copy(cantidadBebida = nuevaCantidad)
        }
        calcularPrecioTotal()
    }

    private fun calcularPrecioTotal() {
        val state = _uiState.value

        val precioPizza = Precios.tamanos
            .find { it.nombre == state.tamanoSeleccionado }
            ?.precio ?: 0.0

        val precioBebida = Precios.bebidas
            .find { it.nombre == state.bebidaSeleccionada }
            ?.precio ?: 0.0

        val total = (precioPizza * state.cantidadPizza) + (precioBebida * state.cantidadBebida)

        _uiState.update {
            it.copy(
                precioPizza = precioPizza,
                precioBebida = precioBebida,
                precioTotal = total
            )
        }
    }
}