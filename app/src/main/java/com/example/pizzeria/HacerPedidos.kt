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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pizzeria.ui.ViewModel.PizzeriaViewModel
import com.example.pizzeria.ui.theme.MiFuenteFamilia
import com.example.pizzeria.ui.theme.onPrimaryLight
import com.example.pizzeria.ui.theme.primaryLight
import com.example.pizzeria.modelo.Precios

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

    val pizzas = listOf("Romana", "Barbacoa", "Margarita")
    val bebidas = listOf("Cola", "Agua", "Sin Bebida")

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color(0xFFFDFD96))
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(top = 16.dp),
                //.verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Total
            Text(
                "Total: ${"%.2f".format(uiState.precioTotal)} €",
                style = MaterialTheme.typography.headlineMedium,
                fontFamily = MiFuenteFamilia,
                modifier = Modifier.padding(all = 16.dp)
            )

            // Selección de pizza
            Text(
                text = stringResource(R.string.txt_seleccionar_pizza),
                fontFamily = MiFuenteFamilia,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                pizzas.forEach { clave ->
                    val nombrePizza = when (clave) {
                        "romana" -> stringResource(R.string.pizza1)
                        "barbacoa" -> stringResource(R.string.pizza2)
                        "margarita" -> stringResource(R.string.pizza3)
                        else -> clave
                    }

                    Button(
                        onClick = {
                            pizzaSeleccionada = clave
                            viewModel.seleccionarPizza(clave)
                            Pizzaexpandedida = true
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (uiState.pizzaSeleccionada == clave) Color(0xFFFFA726) else primaryLight,
                            contentColor = onPrimaryLight
                        ),
                        modifier = Modifier
                            .weight(1F)
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Text(nombrePizza, fontFamily = MiFuenteFamilia, color = Color.White)
                    }
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
                            "Opciones para ${
                                when (pizzaSeleccionada) {
                                    "romana" -> stringResource(R.string.pizza1)
                                    "barbacoa" -> stringResource(R.string.pizza2)
                                    "margarita" -> stringResource(R.string.pizza3)
                                    else -> pizzaSeleccionada
                                }
                            }",
                            fontFamily = MiFuenteFamilia,
                            color = Color.Black,
                            style = MaterialTheme.typography.titleSmall
                        )

                        Spacer(Modifier.height(4.dp))

                        val opciones = when (pizzaSeleccionada) {
                            "romana" -> listOf("Con champiñones", "Sin champiñones")
                            "barbacoa" -> listOf("Cerdo", "Pollo", "Ternera")
                            "margarita" -> listOf(
                                "Con piña y vegana",
                                "Con piña y no vegana",
                                "Sin piña y vegana",
                                "Sin piña y no vegana"
                            )
                            else -> emptyList()
                        }

                        opciones.forEach { opcion ->
                            val selectedOption = uiState.opcionSeleccionada == opcion


                            val imagen1 = when (opcion) {
                                "Con champiñones" -> R.drawable.champi
                                "Sin champiñones" -> R.drawable.sin_champi
                                "Cerdo" -> R.drawable.cerdo
                                "Pollo" -> R.drawable.pollo
                                "Ternera" -> R.drawable.ternera
                                "Con piña y vegana", "Con piña y no vegana" -> R.drawable.pina
                                "Sin piña y vegana", "Sin piña y no vegana" -> R.drawable.sin_pina
                                else -> R.drawable.ic_launcher_foreground
                            }


                            val imagen2 = when (opcion) {
                                "Con piña y vegana", "Sin piña y vegana" -> R.drawable.vegana
                                "Con piña y no vegana", "Sin piña y no vegana" -> R.drawable.no_vegana
                                else -> null
                            }

                            Button(
                                onClick = {
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
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start
                                ) {

                                    Image(
                                        painter = painterResource(id = imagen1),
                                        contentDescription = opcion,
                                        modifier = Modifier.size(36.dp)
                                    )

                                    Spacer(Modifier.width(8.dp))

                                    Text(opcion, fontFamily = MiFuenteFamilia, color = Color.White)

                                    imagen2?.let {
                                        Spacer(Modifier.width(4.dp))
                                        Image(
                                            painter = painterResource(id = it),
                                            contentDescription = opcion,
                                            modifier = Modifier.size(24.dp)
                                        )
                                    }
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
                Precios.tamanos.forEach { t ->
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
                            .weight(1F)
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Column {
                            Text(nombreTraducido, fontFamily = MiFuenteFamilia, color = Color.White)
                            Text("(${t.precio} €)", fontFamily = MiFuenteFamilia, color = Color.White)
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))


            Text(
                text = stringResource(R.string.label_cantidad),
                fontFamily = MiFuenteFamilia,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(Modifier.height(4.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { viewModel.cambiarCantidadPizza(-1) },
                    colors = ButtonDefaults.buttonColors(containerColor = primaryLight)
                ) {
                    Text("-", fontFamily = MiFuenteFamilia, color = Color.White)
                }

                Spacer(Modifier.width(8.dp))
                Text(uiState.cantidadPizza.toString(), fontFamily = MiFuenteFamilia, style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.width(8.dp))

                Button(
                    onClick = { viewModel.cambiarCantidadPizza(1) },
                    colors = ButtonDefaults.buttonColors(containerColor = primaryLight)
                ) {
                    Text("+", fontFamily = MiFuenteFamilia, color = Color.White)
                }
            }

            Spacer(Modifier.height(24.dp))


            Text(
                text = stringResource(R.string.txt_seleccionar_bebida),
                fontFamily = MiFuenteFamilia,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(Modifier.height(8.dp))

            Row {
                bebidas.forEach { clave ->
                    val nombreBebida = when (clave) {
                        "Agua" -> stringResource(R.string.bebida1)
                        "Cola" -> stringResource(R.string.bebida2)
                        "Sin Bebida" -> stringResource(R.string.bebida3)
                        else -> clave
                    }

                    val precioBebida = Precios.bebidas.find { it.nombre.equals(clave) }?.precio ?: 0.0

                    Button(
                        onClick = { viewModel.seleccionarBebida(clave) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (uiState.bebidaSeleccionada == clave) Color(0xFFFFA726) else primaryLight,
                            contentColor = onPrimaryLight
                        ),
                        modifier = Modifier
                            .weight(1F)
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Column {
                            Text(nombreBebida, fontFamily = MiFuenteFamilia, color = Color.White)
                            Text("(${precioBebida} €)", fontFamily = MiFuenteFamilia, color = Color.White)
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.label_cantidad_bebidas),
                fontFamily = MiFuenteFamilia,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(Modifier.height(4.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { viewModel.cambiarCantidadBebida(-1) },
                    colors = ButtonDefaults.buttonColors(containerColor = primaryLight)
                ) {
                    Text("-", fontFamily = MiFuenteFamilia, color = Color.White)
                }

                Spacer(Modifier.width(8.dp))
                Text(uiState.cantidadBebida.toString(), fontFamily = MiFuenteFamilia, style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.width(8.dp))

                Button(
                    onClick = { viewModel.cambiarCantidadBebida(1) },
                    colors = ButtonDefaults.buttonColors(containerColor = primaryLight)
                ) {
                    Text("+", fontFamily = MiFuenteFamilia, color = Color.White)
                }
            }

            Spacer(Modifier.height(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                BotonInicio(onClick = { onBotonInicioPulsado("Atrás") })
                Spacer(Modifier.width(16.dp))
                BotonResumenPedido(onClick = { onBotonResumenPedidoPulsado("Resumen Pedido") })
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
fun BotonResumenPedido(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(onClick = onClick, modifier = modifier) {
        Text(text = stringResource(R.string.resumen_pedido), fontFamily = MiFuenteFamilia)
    }
}

@Composable
fun BotonInicio(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(onClick = onClick, modifier = modifier) {
        Text(text = stringResource(R.string.atras), fontFamily = MiFuenteFamilia)
    }
}
