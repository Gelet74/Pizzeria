package com.example.pizzeria.modelo

object Precios {

    data class Tamano(val nombre: String, val precio: Double)
    data class Bebida(val nombre: String, val precio: Double)

    val tamanos = listOf(
        Tamano("Pequeña", 4.95),
        Tamano("Mediana", 6.95),
        Tamano("Grande", 10.95)
    )

    val bebidas = listOf(
        Bebida("Agua", 2.0),
        Bebida("Cola", 2.5),
        Bebida("Sin bebida", 0.0)
    )

    val tiposPizza = listOf("Romana", "Barbacoa", "Margarita")
}