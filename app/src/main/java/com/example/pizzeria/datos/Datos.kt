package com.example.pizzeria.datos

import com.example.pizzeria.modelo.Pedido

class Datos {
    fun cargarPedidos(): List<Pedido> {
        return listOf(
            Pedido(
                idpedido = 1,
                pizza = "Barbacoa",
                tamanoPizza = "Mediana",
                opcionPizza = "Pollo",
                bebida = "Agua",
                cantidadPizza = 3,
                cantidadBebida = 2,
                precioPizza = 0.0,
                precioBebida = 0.0,
                precioTotal = 0.0,
                tipoTarjeta = "Visa"
            ),
            Pedido(
                idpedido = 2,
                pizza = "Margarita",
                tamanoPizza = "Pequeña",
                opcionPizza = "Con piña y vegana",
                bebida = "Cola",
                cantidadPizza = 4,
                cantidadBebida = 4,
                precioPizza = 0.0,
                precioBebida = 0.0,
                precioTotal = 0.0,
                tipoTarjeta = "Visa"
            ),
            Pedido(
                idpedido = 3,
                pizza = "Romana",
                tamanoPizza = "Grande",
                bebida = "Sin Bebida",
                opcionPizza = "Con champiñones",
                cantidadPizza = 1,
                cantidadBebida = 0,
                precioPizza = 0.0,
                precioBebida = 0.0,
                precioTotal = 0.0,
                tipoTarjeta = "Euro 6000"
            ),
            Pedido(
                idpedido = 4,
                pizza = "Barbacoa",
                tamanoPizza = "Grande",
                opcionPizza = "Cerdo",
                bebida = "Cola",
                cantidadPizza = 3,
                cantidadBebida = 6,
                precioPizza = 0.0,
                precioBebida = 0.0,
                precioTotal = 0.0,
                tipoTarjeta = "MasterCard"
            )
        )
    }
}
