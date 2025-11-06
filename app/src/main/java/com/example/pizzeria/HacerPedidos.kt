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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pizzeria.ui.ViewModel.PizzeriaViewModel
import com.example.pizzeria.ui.theme.MiFuenteFamilia
import com.example.pizzeria.ui.theme.onPrimaryLight
import com.example.pizzeria.ui.theme.primaryLight

@Composable
fun HacerPedido(
    viewModel: PizzeriaViewModel = viewModel(),
    modifier: Modifier = Modifier,
    onBotonInicioPulsado: (String) -> Unit,
    onBotonResumenPedidoPulsado: (String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    var pizzaSeleccionada by remember { mutableStateOf("") }
    var Pizzaexpandedida by remember { mutableStateOf(false) }

    val pizzas = listOf(
        stringResource(R.string.pizza1),
        stringResource(R.string.pizza2),
        stringResource(R.string.pizza3)
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color(0xFFFDFD96))
    ) {
        Text(
            "Total: ${"%.2f".format(uiState.precioTotal)} €",
            style = MaterialTheme.typography.headlineMedium,
            fontFamily = MiFuenteFamilia,
            modifier = Modifier.padding(all = 16.dp)
        )

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(top = 70.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // ---------- PIZZAS ----------
            Text(
                text = stringResource(R.string.txt_seleccionar_pizza),
                fontFamily = MiFuenteFamilia,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                pizzas.forEach { tipo ->
                    Button(
                        onClick = {
                            pizzaSeleccionada = tipo
                            viewModel.seleccionarPizza(tipo)
                            Pizzaexpandedida = true
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (uiState.pizzaSeleccionada == tipo) Color(0xFFFFA726) else primaryLight,
                            contentColor = onPrimaryLight
                        ),
                        modifier = Modifier
                            .weight(1F)
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Text(tipo, fontFamily = MiFuenteFamilia, color = Color.White)
                    }
                }

                if (Pizzaexpandedida) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        )
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                "Opciones para $pizzaSeleccionada",
                                fontFamily = MiFuenteFamilia,
                                color = Color.Black,
                                style = MaterialTheme.typography.titleSmall
                            )

                            Spacer(Modifier.height(4.dp))

                            val opciones = when (pizzaSeleccionada) {
                                stringResource(R.string.pizza1) -> listOf(
                                    stringResource(R.string.opcion_champi),
                                    stringResource(R.string.opcion_sin_champi)
                                )
                                stringResource(R.string.pizza2) -> listOf(
                                    stringResource(R.string.opcion_cerdo),
                                    stringResource(R.string.opcion_pollo),
                                    stringResource(R.string.opcion_ternera)
                                )
                                stringResource(R.string.pizza3) -> listOf(
                                    stringResource(R.string.opcion_con_pina_vegana),
                                    stringResource(R.string.opcion_con_pina_no_vegana),
                                    stringResource(R.string.opcion_sin_pina_vegana),
                                    stringResource(R.string.opcion_sin_pina_no_vegana)
                                )
                                else -> emptyList()
                            }

                            opciones.forEach { opcion ->
                                val selectedOption = uiState.opcionSeleccionada == opcion

                                Button(
                                    onClick = {
                                        viewModel.seleccionarPizza(pizzaSeleccionada)
                                        viewModel.seleccionarOpcionPizza(opcion)
                                        Pizzaexpandedida = false
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (selectedOption) primaryLight else Color.Gray,
                                        contentColor = onPrimaryLight
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 2.dp)
                                ) {
                                    Text(opcion, fontFamily = MiFuenteFamilia, color = Color.White)
                                }
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))


            Text(
                text = stringResource(R.string.label_seleccionar_tamano),
                fontFamily = MiFuenteFamilia,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(Modifier.height(8.dp))

            Row {
                com.example.pizzeria.modelo.Precios.tamanos.forEach { t ->
                    val nombreTraducido = when (t.nombre) {
                        "Pequeña" -> stringResource(R.string.tamano_pequena)
                        "Mediana" -> stringResource(R.string.tamano_mediana)
                        "Grande" -> stringResource(R.string.tamano_grande)
                        else -> t.nombre
                    }
                    Button(
                        onClick = { viewModel.seleccionarTamano(t.nombre) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (uiState.tamanoSeleccionado == t.nombre) Color(0xFFFFA726) else primaryLight,
                            contentColor = onPrimaryLight
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1F)
                            .padding(vertical = 4.dp)
                    ) {
                        Column {
                            Text(nombreTraducido, fontFamily = MiFuenteFamilia, color = Color.White)
                            Text("(${t.precio} €)", fontFamily = MiFuenteFamilia, color = Color.White)
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))


            Text(
                text = stringResource(R.string.txt_seleccionar_bebida),
                fontFamily = MiFuenteFamilia,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(Modifier.height(8.dp))

            val bebidas = listOf(
                stringResource(R.string.bebida1),
                stringResource(R.string.bebida2),
                stringResource(R.string.bebida3)
            )

            Row {
                bebidas.forEach { b ->
                    Button(
                        onClick = { viewModel.seleccionarBebida(b) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (uiState.bebidaSeleccionada == b) Color(0xFFFFA726) else primaryLight,
                            contentColor = onPrimaryLight
                        ),
                        modifier = Modifier
                            .weight(1F)
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Text(b, fontFamily = MiFuenteFamilia, color = Color.White)
                    }
                }
            }

            Spacer(Modifier.height(32.dp))

            // ---------- BOTONES INFERIORES ----------
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                BotonInicio(onClick = { onBotonInicioPulsado("Atrás") })
                BotonResumenPedido(onClick = { onBotonResumenPedidoPulsado("Resumen Pedido") })
            }
        }
    }
}

@Composable
fun BotonResumenPedido(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(onClick = onClick, modifier = modifier) {
        Text(
            text = stringResource(R.string.resumen_pedido),
            fontFamily = MiFuenteFamilia
        )
    }
}

@Composable
fun BotonInicio(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(onClick = onClick, modifier = modifier) {
        Text(
            text = stringResource(R.string.atras),
            fontFamily = MiFuenteFamilia
        )
    }
}
