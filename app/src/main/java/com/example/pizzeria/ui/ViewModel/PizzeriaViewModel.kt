package com.example.pizzeria.ui.ViewModel

import androidx.lifecycle.ViewModel
import com.example.pizzeria.R
import com.example.pizzeria.datos.Datos
import com.example.pizzeria.modelo.Pedido
import com.example.pizzeria.modelo.PizzeriaUIState
import com.example.pizzeria.modelo.Precios
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


data class OpcionPago(
    val nombre: String,
    val iconoResId: Int
)

class PizzeriaViewModel : ViewModel() {


    private val _uiState = MutableStateFlow(PizzeriaUIState())
    val uiState: StateFlow<PizzeriaUIState> = _uiState.asStateFlow()

    private val _pedidos = MutableStateFlow<List<Pedido>>(emptyList())
    val pedidos: StateFlow<List<Pedido>> = _pedidos.asStateFlow()

    init {
        cargarPedidos()
    }

    fun seleccionarPizza(nombrePizza: String) {
        _uiState.update { current ->
            current.copy(
                pizzaSeleccionada = nombrePizza,
                cantidadPizza = 1
            )
        }
        calcularPrecioTotal()
    }

    fun seleccionarOpcionPizza(opcion: String) {
        _uiState.update { current ->
            current.copy(opcionSeleccionada = opcion)
        }
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

    private fun cargarPedidos() {
        val pedidosConPrecios = Datos().cargarPedidos().map { pedido ->
            val precioPizza = Precios.tamanos
                .find { it.nombre.equals(pedido.tamanoPizza, ignoreCase = true) }
                ?.precio ?: 0.0

            val precioBebida = Precios.bebidas
                .find { it.nombre.equals(pedido.bebida, ignoreCase = true) }
                ?.precio ?: 0.0

            val total = (precioPizza * pedido.cantidadPizza) + (precioBebida * pedido.cantidadBebida)

            pedido.copy(
                precioPizza = precioPizza,
                precioBebida = precioBebida,
                precioTotal = total
            )
        }

        _pedidos.value = pedidosConPrecios
    }

    val opcionesPago = listOf(
        OpcionPago("VISA", R.drawable.visa),
        OpcionPago("MasterCard", R.drawable.mastercard),
        OpcionPago("Euro 6000", R.drawable.euro6000)
    )


    private val _tipoPagoSeleccionado = MutableStateFlow(opcionesPago.first())
    val tipoPagoSeleccionado: StateFlow<OpcionPago> = _tipoPagoSeleccionado.asStateFlow()

    // Campos del formulario
    private val _numeroTarjeta = MutableStateFlow("")
    val numeroTarjeta: StateFlow<String> = _numeroTarjeta.asStateFlow()

    private val _fechaCaducidad = MutableStateFlow("")
    val fechaCaducidad: StateFlow<String> = _fechaCaducidad.asStateFlow()

    private val _cvc = MutableStateFlow("")
    val cvc: StateFlow<String> = _cvc.asStateFlow()

    private val _formularioValido = MutableStateFlow(false)
    val formularioValido: StateFlow<Boolean> = _formularioValido.asStateFlow()

    fun seleccionarOpcionPago(opcion: OpcionPago) {
        _tipoPagoSeleccionado.value = opcion
        validarCampos()
    }

    fun actualizarNumeroTarjeta(valor: String) {
        if (valor.length <= 16 && valor.all { it.isDigit() }) {
            _numeroTarjeta.value = valor
            validarCampos()
        }
    }

    fun actualizarFechaCaducidad(valor: String) {
        if (valor.length <= 5) {
            _fechaCaducidad.value = valor
            validarCampos()
        }
    }

    fun actualizarCvc(valor: String) {
        if (valor.length <= 4 && valor.all { it.isDigit() }) {
            _cvc.value = valor
            validarCampos()
        }
    }

    private fun validarCampos() {
        _formularioValido.value =
            _numeroTarjeta.value.length == 16 &&
                    _fechaCaducidad.value.length == 5 &&
                    _cvc.value.length in 3..4
    }
}
