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
                text = stringResource(R.string.resumen_de_pedidos),
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
                modifier = Modifier
                    .padding(bottom = 16.dp)
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

   val pizzaTraducida = when (pedido.pizza) {
        "romana" -> stringResource(R.string.pizza1)
        "barbacoa" -> stringResource(R.string.pizza2)
        "margarita" -> stringResource(R.string.pizza3)
        else -> pedido.pizza
    }

    val tamanoTraducido = when (pedido.tamanoPizza) {
        "Pequeña" -> stringResource(R.string.tamano_pequena)
        "Mediana" -> stringResource(R.string.tamano_mediana)
        "Grande" -> stringResource(R.string.tamano_grande)
        else -> pedido.tamanoPizza
    }

    val bebidaTraducida = when (pedido.bebida) {
        "Cola" -> stringResource(R.string.bebida1)
        "Agua" -> stringResource(R.string.bebida2)
        "Sin Bebida" -> stringResource(R.string.bebida3)
        else -> pedido.bebida
    }
    val opcionTraducida = when (pedido.opcionPizza) {
       "Con champiñones" -> stringResource(R.string.opcion_champi)
       "Sin champiñones" -> stringResource(R.string.opcion_sin_champi)
        "Cerdo" -> stringResource(R.string.opcion_cerdo)
        "Pollo" -> stringResource(R.string.opcion_pollo)
        "Ternera" -> stringResource(R.string.opcion_ternera)
        "Con piña y vegana" -> stringResource(R.string.opcion_con_pina_vegana)
        "Con piña y no vegana" ->stringResource(R.string.opcion_con_pina_no_vegana)
        "Sin piña y vegana" -> stringResource(R.string.opcion_sin_pina_vegana)
        "Sin piña y no vegana" ->stringResource(R.string.opcion_sin_pina_no_vegana)
        else -> pedido.opcionPizza
    }
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
                        text = stringResource(R.string.pedido, pedido.idpedido, pizzaTraducida),
                        fontFamily = MiFuenteFamilia,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Pizza: $pizzaTraducida",
                        fontFamily = MiFuenteFamilia,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = stringResource(R.string.bebida) + ": $bebidaTraducida",
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
                Text(stringResource(R.string.label_tamano)+": $tamanoTraducido", fontFamily = MiFuenteFamilia)
                Text(stringResource(R.string.label_cantidad)+" ${pedido.cantidadPizza}", fontFamily = MiFuenteFamilia)
                Text(stringResource(R.string.label_opcion)+" ${opcionTraducida}", fontFamily = MiFuenteFamilia)
                Text(stringResource(R.string.label_cantidad_bebidas)+": ${pedido.cantidadBebida}", fontFamily = MiFuenteFamilia)
                Text(stringResource(R.string.tipo_tarj)+": ${pedido.tipoTarjeta}", fontFamily = MiFuenteFamilia)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.txt_precio_total)+": ${"%.2f".format(pedido.precioTotal)}€",
                    style = MaterialTheme.typography.titleLarge,
                    fontFamily = MiFuenteFamilia
                )
            }
        }
    }
}

