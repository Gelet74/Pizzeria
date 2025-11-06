package com.example.pizzeria

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pizzeria.modelo.Precios
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

    Box(modifier = modifier.fillMaxSize()

        .background(color = Color(0xFFFDFD96))) {

        Text(
            "Total: ${"%.2f".format(uiState.precioTotal)} €",
            style = MaterialTheme.typography.headlineMedium,
            fontFamily = MiFuenteFamilia,
            modifier = Modifier.padding(all = 16.dp)

        )


        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(top = 8.dp)
                .padding(top = 70.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        )

        {
            Text (text= stringResource(R.string.txt_seleccionar_pizza),
                fontFamily = MiFuenteFamilia,
                style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(4.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ){

                Precios.tiposPizza.forEach { tipo ->
                    val isSelected = uiState.pizzaSeleccionada.contains(tipo)
                    Button(
                        onClick = {
                            pizzaSeleccionada = tipo
                            viewModel.seleccionarPizza(tipo)
                            Pizzaexpandedida = true
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (uiState.pizzaSeleccionada==tipo) Color(0xFFFFA726) else primaryLight,
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
                                fontFamily = MiFuenteFamilia, color = Color.Black,
                                style = MaterialTheme.typography.titleSmall
                            )
                            Spacer(Modifier.height(4.dp))

                            val opciones = when (pizzaSeleccionada) {
                                "Romana" -> listOf("Con champiñones", "Sin champiñones")
                                "Barbacoa" -> listOf("Cerdo", "Pollo", "Ternera")
                                "Margarita" -> listOf(
                                    "Con piña y vegana",
                                    "Con piña y no vegana",
                                    "Sin piña y vegana",
                                    "Sin piña y no vegana"
                                )

                                else -> emptyList()
                            }

                            opciones.forEach { opcion ->
                                val selectedOption = uiState.opcionSeleccionada == opcion


                                val imagenInicio = when (opcion) {
                                    "Con champiñones" -> R.drawable.champi
                                    "Sin champiñones" -> R.drawable.sin_champi
                                    "Cerdo" -> R.drawable.cerdo
                                    "Pollo" -> R.drawable.pollo
                                    "Ternera" -> R.drawable.ternera
                                    "Con piña y vegana", "Con piña y no vegana"-> R.drawable.pina
                                    "Sin piña y vegana", "Sin piña y no vegana" -> R.drawable.sin_pina
                                    else -> R.drawable.ic_launcher_foreground
                                }


                                val imagenFinal = when (opcion) {
                                    "Con piña y vegana", -> R.drawable.vegana
                                    "Sin piña y vegana"  -> R.drawable.vegana
                                    "Con piña y no vegana"  -> R.drawable.no_vegana
                                    "Sin piña y no vegana" -> R.drawable.no_vegana
                                    else -> null
                                }

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
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {

                                        Image(
                                            painter = painterResource(id = imagenInicio),
                                            contentDescription = "Imagen $opcion",
                                            modifier = Modifier.size(36.dp)
                                        )


                                        Text(
                                            opcion,
                                            fontFamily = MiFuenteFamilia,
                                            color = Color.White,
                                            modifier = Modifier.weight(1f),
                                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                        )


                                        imagenFinal?.let {
                                            Image(
                                                painter = painterResource(id = it),
                                                contentDescription = "Icono extra $opcion",
                                                modifier = Modifier.size(36.dp)
                                            )
                                        }
                                    }
                                }
                            }

                            Spacer(Modifier.height(4.dp))
                        }
                    }
                }
            }

            Spacer(Modifier.height(4.dp))

            Text(text = stringResource(R.string.label_seleccionar_tamano),
                fontFamily = MiFuenteFamilia,
                style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            Row {
                Precios.tamanos.forEach { t ->
                    val isSelected = uiState.tamanoSeleccionado == t.nombre
                    Button(
                        onClick = { viewModel.seleccionarTamano(t.nombre) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (uiState.tamanoSeleccionado==t.nombre) Color(0xFFFFA726) else primaryLight,
                            contentColor = onPrimaryLight
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1F)
                            .padding(vertical = 4.dp)
                    ) {
                        Column {
                            Text(
                                "${t.nombre}",
                                fontFamily = MiFuenteFamilia,
                                color = Color.White
                            )
                            Text(
                                "(${t.precio} €)",
                                fontFamily = MiFuenteFamilia,
                                color = Color.White
                            )

                        }
                    }
                }
            }
            Spacer(Modifier.height(24.dp))

            Text(text = stringResource(R.string.txt_seleccionar_bebida),
                fontFamily = MiFuenteFamilia,
                style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))

            Row {
                Precios.bebidas.forEach { b ->
                    val isSelected = uiState.bebidaSeleccionada == b.nombre
                    Button(
                        onClick = { viewModel.seleccionarBebida(b.nombre) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (uiState.bebidaSeleccionada==b.nombre) Color(0xFFFFA726) else primaryLight,
                            contentColor = onPrimaryLight
                        ),
                        modifier = Modifier
                            .weight(1F)
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        if( b.nombre == "Sin bebida")
                            Text(b.nombre, fontFamily = MiFuenteFamilia, color = Color.White)
                        else
                            Column {
                                Text(
                                    "${b.nombre}",
                                    fontFamily = MiFuenteFamilia,
                                    color = Color.White
                                )
                                Text(
                                    "(${b.precio} €)",
                                    fontFamily = MiFuenteFamilia,
                                    color = Color.White
                                )

                            }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            Text (text = stringResource(R.string.label_cantidad),
                fontFamily = MiFuenteFamilia,
                style = MaterialTheme.typography.titleMedium)
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = { viewModel.cambiarCantidadPizza(-1) }) { Text("-") }
                Text(uiState.cantidadPizza.toString(), modifier = Modifier.padding(horizontal = 16.dp))
                Button(onClick = { viewModel.cambiarCantidadPizza(1) }) { Text("+") }
            }
            Spacer(Modifier.height(16.dp))
            Text(text = stringResource(R.string.label_cantidad_bebidas),
                fontFamily = MiFuenteFamilia,
                style = MaterialTheme.typography.titleMedium)
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = { viewModel.cambiarCantidadBebida(-1) }) { Text("-") }
                Text(uiState.cantidadBebida.toString(), modifier = Modifier.padding(horizontal = 16.dp))
                Button(onClick = { viewModel.cambiarCantidadBebida(1) }) { Text("+") }
            }

            Spacer(Modifier.height(32.dp))

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
fun BotonResumenPedido(onClick: () -> Unit,
                       modifier: Modifier = Modifier)
    {  Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.resumen_pedido),
            fontFamily = MiFuenteFamilia
        )
    }
}

@Composable
fun BotonInicio(onClick: () -> Unit,
                modifier: Modifier = Modifier)
        {  Button(
            onClick = onClick,
            modifier = modifier
        ) {
            Text(
                text = stringResource(R.string.atras),
                fontFamily = MiFuenteFamilia
            )
        }
}