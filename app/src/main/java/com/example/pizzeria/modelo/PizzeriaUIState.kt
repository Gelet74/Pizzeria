package com.example.pizzeria.modelo

data class PizzeriaUIState(

    val pizzaRomanaNombre:String = "Romana",
    val pizzaBarbacoaNombre:String = "Barbacoa",
    val pizzaMargaritaNombre:String = "Margarita",

    val pizzaSeleccionada: String = "",
    val tamanoSeleccionado: String = "",
    val bebidaSeleccionada: String = "",

    val cantidadPizza: Int = 1,
    val cantidadBebida: Int = 1,
    val precioTotal: Double = 0.0,


    val idpedido: Int = 0,
    val pizza: String = "",
    val tamanoPizza: String = "",
    val bebida: String = "",
    val precioPizza: Double = 0.0,
    val precioBebida: Double = 0.0
)
