package com.example.pizzeria

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pizzeria.modelo.Pedido
import com.example.pizzeria.ui.ViewModel.PizzeriaViewModel
import com.example.pizzeria.ui.theme.MiFuenteFamilia

@Composable
fun Verpedidos(
    viewModel: PizzeriaViewModel = viewModel(),
    onBotonInicioVerPedidosPulsado: () -> Unit
) {
    val pedidos by viewModel.pedidos.collectAsState()
    Box(
        modifier = Modifier
            .background(color = Color(0xFFFDFD96))
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Resumen de Pedidos",
                fontFamily = MiFuenteFamilia,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(pedidos) { pedido ->
                    PedidoTarjetaExpandible(pedido = pedido)
                }
            }

           Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onBotonInicioVerPedidosPulsado,
                modifier = Modifier .padding(bottom = 16.dp)
                                    .width(120.dp)
                                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = stringResource(R.string.atras),
                    fontFamily = MiFuenteFamilia
                )
            }
        }
    }
}
@Composable
fun PedidoTarjetaExpandible(pedido: Pedido, modifier: Modifier = Modifier) {

    var expandido by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .background(Color(0xFFFDFD96))
                .padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Pedido #${pedido.idpedido}: ${pedido.pizza}",
                        fontFamily = MiFuenteFamilia,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Bebida: ${pedido.bebida}",
                        fontFamily = MiFuenteFamilia,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { expandido = !expandido }) {
                    Icon(
                        imageVector = if (expandido) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                        contentDescription = if (expandido) "Colapsar" else "Expandir"
                    )
                }
            }

            if (expandido) {
                Spacer(modifier = Modifier.padding(vertical = 8.dp))
                Text("Tamaño: ${pedido.tamanoPizza}",
                        fontFamily = MiFuenteFamilia)
                Text("Cantidad de Pizzas: ${pedido.cantidadPizza}",
                    fontFamily = MiFuenteFamilia)
                Text("Opcion Pizza: ${pedido.opcionPizza}",
                    fontFamily = MiFuenteFamilia)
                Text("Cantidad de Bebidas: ${pedido.cantidadBebida}",
                    fontFamily = MiFuenteFamilia)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "PRECIO TOTAL: ${"%.2f".format(pedido.precioTotal)}€",
                    style = MaterialTheme.typography.titleLarge,
                    fontFamily = MiFuenteFamilia
                )
            }
        }
    }
}
