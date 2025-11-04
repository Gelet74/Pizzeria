package com.example.pizzeria.modelo

data class Pedido(
    val idpedido: Int,
    val pizza: String,
    val tamanoPizza: String,
    val bebida: String,
    val cantidadPizza: Int,
    val cantidadBebida: Int,
    val precioPizza: Double,
    val precioBebida: Double,
    val precioTotal: Double
)