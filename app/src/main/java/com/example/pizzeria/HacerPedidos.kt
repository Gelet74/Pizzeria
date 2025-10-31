package com.example.pizzeria

import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    var pizzaSeleccionada by remember { mutableStateOf("") }
    var Pizzaexpandedida by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Start
        ){
            Text(
                "Total: ${"%.2f".format(uiState.precioTotal)} €",
                style = MaterialTheme.typography.headlineMedium,
                fontFamily = MiFuenteFamilia,
                modifier = Modifier.padding(end = 16.dp)
            )
            Column {
                Text(
                    text = "Pizza: ${uiState.pizzaSeleccionada}",
                    fontFamily = MiFuenteFamilia,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = "Tamaño: ${uiState.tamanoSeleccionado}",
                    fontFamily = MiFuenteFamilia,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text (
                    text = "Cantidad: ${uiState.cantidadPizza}",
                    fontFamily = MiFuenteFamilia,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text (
                    text = "Bebida: ${uiState.bebidaSeleccionada}",
                    fontFamily = MiFuenteFamilia,
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 125.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Text (text= stringResource(R.string.txt_seleccionar_pizza),
            fontFamily = MiFuenteFamilia,
            style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))

        Precios.tiposPizza.forEach { tipo ->
            val isSelected = uiState.pizzaSeleccionada.contains(tipo)
            Button(
                onClick = {
                    pizzaSeleccionada = tipo
                    Pizzaexpandedida = true
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryLight,
                    contentColor = onPrimaryLight
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(tipo, fontFamily = MiFuenteFamilia, color = Color.Black)

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
                    Text("Opciones para $pizzaSeleccionada",
                    fontFamily = MiFuenteFamilia, color = Color.Black,
                        style = MaterialTheme.typography.titleSmall)
                    Spacer(Modifier.height(8.dp))

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
                        val selectedOption = uiState.pizzaSeleccionada.contains(opcion)
                        Button(
                            onClick = {
                                viewModel.seleccionarPizza("$pizzaSeleccionada ($opcion)")
                                Pizzaexpandedida = false
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = primaryLight,
                                contentColor = onPrimaryLight
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp)
                        ) {
                            Text(opcion, fontFamily = MiFuenteFamilia, color = Color.Black)

                        }
                    }

                    Spacer(Modifier.height(4.dp))
                    TextButton(onClick = { Pizzaexpandedida = false }) {
                        Text(text = stringResource(R.string.btn_cerrar)
                            , fontFamily = MiFuenteFamilia)
                    }
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        Text(text = stringResource(R.string.label_seleccionar_tamano),
            fontFamily = MiFuenteFamilia,
            style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))
        Precios.tamanos.forEach { t ->
            val isSelected = uiState.tamanoSeleccionado == t.nombre
            Button(
                onClick = { viewModel.seleccionarTamano(t.nombre) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryLight,
                    contentColor = onPrimaryLight
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text("${t.nombre} (${t.precio} €)", fontFamily = MiFuenteFamilia, color = Color.Black)
            }
        }

        Spacer(Modifier.height(24.dp))

        Text(text = stringResource(R.string.txt_seleccionar_bebida),
            fontFamily = MiFuenteFamilia,
            style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))
        Precios.bebidas.forEach { b ->
            val isSelected = uiState.bebidaSeleccionada == b.nombre
            Button(
                onClick = { viewModel.seleccionarBebida(b.nombre) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryLight,
                    contentColor = onPrimaryLight
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text("${b.nombre} (${b.precio} €)", fontFamily = MiFuenteFamilia, color = Color.Black)
            }
        }

        Spacer(Modifier.height(24.dp))

        Text (text = stringResource(R.string.txt_seleccionar_pizza),
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

        if (uiState.bebidaSeleccionada.isNotEmpty() && uiState.bebidaSeleccionada != stringResource(R.string.bebida3)) {

            Spacer(Modifier.height(16.dp))
            Text(text = stringResource(R.string.txt_seleccionar_bebida),
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
        }
        Spacer(Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(onClick = { }) { Text (text = stringResource(R.string.btn_cancelar),
                                          fontFamily = MiFuenteFamilia) }
            Button(onClick = { }) { Text(text = stringResource(R.string.btn_aceptar),
                                         fontFamily = MiFuenteFamilia) }
        }

        Spacer(Modifier.height(16.dp))
    }
}
}