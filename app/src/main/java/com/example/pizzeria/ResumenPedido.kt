package com.example.pizzeria

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pizzeria.ui.ViewModel.PizzeriaViewModel
import com.example.pizzeria.ui.theme.MiFuenteFamilia
import com.example.pizzeria.ui.theme.PizzeriaTheme

@Composable
fun ResumenPedido(
    viewModel: PizzeriaViewModel = viewModel(),
    modifier: Modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
    onBotonCancelarPulsado: (String) -> Unit,
    onBotonPagoPulsado: (String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .background(color = Color(0xFFFDFD96))
    )
    {
        Column(
            modifier = modifier
                .fillMaxSize()
                //.padding(4.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                    //.padding(bottom = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(
                        id = obtenerImagenPizza(uiState.pizzaSeleccionada)
                            ?: R.drawable.ic_launcher_foreground
                    ),
                    contentDescription = null,
                    modifier = Modifier.size(180.dp)
                )
            }

            Text(
                text = stringResource(R.string.resumen_title),
                style = MaterialTheme.typography.headlineLarge,
                fontFamily = MiFuenteFamilia,
                modifier = Modifier.padding(bottom = 4.dp)
            )


            Card(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.padding(8.dp)) {
                    Column(modifier = Modifier.padding(4.dp)) {
                        Text(
                            "Pizza: ${uiState.pizzaSeleccionada}",
                            fontFamily = MiFuenteFamilia,
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Text(
                            "Tamaño: ${uiState.tamanoSeleccionado}",
                            fontFamily = MiFuenteFamilia,
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Text(
                            "Cantidad pizzas: ${uiState.cantidadPizza}",
                            fontFamily = MiFuenteFamilia,
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                    val imagenesOpcion = obtenerImagenOpcionPizza(
                        uiState.pizzaSeleccionada,
                        uiState.opcionSeleccionada
                    )

                    if (imagenesOpcion != null) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Image(
                                painter = painterResource(id = imagenesOpcion.first),
                                contentDescription = null,
                                modifier = Modifier.size(120.dp)
                            )
                            imagenesOpcion.second?.let {
                                Spacer(Modifier.width(8.dp))
                                Image(
                                    painter = painterResource(id = it),
                                    contentDescription = null,
                                    modifier = Modifier.size(120.dp)
                                )
                            }
                        }
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.ic_launcher_foreground),
                            contentDescription = null,
                            modifier = Modifier.size(180.dp)
                        )
                    }




                }
            }
            Spacer(modifier = Modifier.height(4.dp))

            Card(modifier = Modifier.fillMaxWidth()) {
                Row {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "Bebida: ${uiState.bebidaSeleccionada}",
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Text(
                            "Cantidad bebidas: ${uiState.cantidadBebida}",
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                    Image(
                        painter = painterResource(
                            id = obtenerImagenBebida(uiState.bebidaSeleccionada)
                                ?: R.drawable.ic_launcher_foreground
                        ),
                        contentDescription = null,
                        modifier = Modifier.size(180.dp)
                    )


                }
            }
            Spacer(modifier = Modifier.height(8.dp))


            Text(
                text = "TOTAL: ${"%.2f".format(uiState.precioTotal)} €",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(32.dp))


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                BotonCancelar(onClick = { onBotonCancelarPulsado("Inicio") })
                BotonPago(onClick = { onBotonPagoPulsado("Formulario Pago") })

            }
        }
    }
}
@Composable
fun BotonCancelar(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.atras),
            fontFamily = MiFuenteFamilia
        )
    }
}
@Composable
fun BotonPago(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.pago_form_title),
            fontFamily = MiFuenteFamilia
        )
    }
}


fun obtenerImagenPizza(nombre: String?): Int? {
    return when (nombre?.lowercase()?.trim()) {
        "romana" -> R.drawable.romana
        "barbacoa" -> R.drawable.barbacoa
        "margarita" -> R.drawable.margarita
        else -> null
    }
}


fun obtenerImagenOpcionPizza(nombrePizza: String, opcion: String): Pair<Int, Int?>? {
    return when (nombrePizza.lowercase()) {

        "romana" -> when (opcion.lowercase()) {
            "con champiñones", "with mushrooms" -> R.drawable.champi to null
            "sin champiñones", "without mushrooms" -> R.drawable.sin_champi to null
            else -> null
        }

        "barbacoa" -> when (opcion.lowercase()) {
            "pollo", "chicken meat" -> R.drawable.pollo to null
            "cerdo", "pork meat" -> R.drawable.cerdo to null
            "ternera", "beef meat" -> R.drawable.ternera to null
            else -> null
        }

        "margarita" -> when (opcion.lowercase()) {
            "con piña y vegana", "with pineapple and vegan" -> R.drawable.pina to R.drawable.vegana
            "con piña y no vegana", "with pineapple and non-vegan" -> R.drawable.pina to R.drawable.no_vegana
            "sin piña y vegana", "without pineapple and vegan" -> R.drawable.sin_pina to R.drawable.vegana
            "sin piña y no vegana", "without pineapple and non-vegan" -> R.drawable.sin_pina to R.drawable.no_vegana
            else -> null
        }

        else -> null
    }
}


fun obtenerImagenBebida(nombre: String?): Int? {
    return when (nombre?.lowercase()?.trim()) {
        "agua" -> R.drawable.agua
        "cola" -> R.drawable.cola
        "sin_bebida", "sin bebida", "no drink" -> R.drawable.sin_bebida
        else -> null
    }
}

@Preview(showBackground = true)
@Composable
fun ResumenPedidoPreview() {
    PizzeriaTheme {
        ResumenPedido(onBotonCancelarPulsado = {},
            onBotonPagoPulsado = {})
    }
}
