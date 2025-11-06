package com.example.pizzeria.modelo

data class PizzeriaUIState(

    val pizzaSeleccionada: String = "",
    val opcionSeleccionada: String = "",
    val tamanoSeleccionado: String = "",
    val bebidaSeleccionada: String = "",

    val cantidadPizza: Int = 1,
    val cantidadBebida: Int = 0,
    val precioTotal: Double = 0.0,


    val idpedido: Int = 0,
    val pizza: String = "",
    val tamanoPizza: String = "",
    val bebida: String = "",
    val precioPizza: Double = 0.0,
    val precioBebida: Double = 0.0,
    val tipoTarjeta: String = ""
)
