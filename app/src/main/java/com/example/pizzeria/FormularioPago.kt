package com.example.pizzeria

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pizzeria.ui.ViewModel.PizzeriaViewModel
import com.example.pizzeria.ui.theme.MiFuenteFamilia

@Composable
fun FormularioPago(
    viewModel: PizzeriaViewModel = viewModel(),
    onBotonInicioPagoPulsado: (String) -> Unit,
    onBotonResumenPagoPulsado: (String) -> Unit
) {
    val opciones = viewModel.opcionesPago
    val tipoSeleccionado by viewModel.tipoPagoSeleccionado.collectAsState()
    val numeroTarjeta by viewModel.numeroTarjeta.collectAsState()
    val fechaCaducidad by viewModel.fechaCaducidad.collectAsState()
    val cvc by viewModel.cvc.collectAsState()
    val formularioValido by viewModel.formularioValido.collectAsState()
    Box(
        modifier = Modifier
            .background(color = Color(0xFFFDFD96))
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Método de Pago",
                style = MaterialTheme.typography.headlineLarge,
                fontFamily = MiFuenteFamilia,
                modifier = Modifier.padding(bottom = 24.dp, top = 8.dp)
            )

            Text("Selecciona tu tarjeta",
                fontFamily = MiFuenteFamilia,
                style = MaterialTheme.typography.titleMedium)

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                shape = RoundedCornerShape(8.dp),
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    opciones.forEach { opcion ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .selectable(
                                    selected = (opcion == tipoSeleccionado),
                                    onClick = { viewModel.seleccionarOpcionPago(opcion) }
                                )
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (opcion == tipoSeleccionado),
                                onClick = { viewModel.seleccionarOpcionPago(opcion) }
                            )
                            Spacer(Modifier.width(8.dp))
                            Image(
                                painter = painterResource(id = opcion.iconoResId),
                                contentDescription = opcion.nombre,
                                modifier = Modifier.size(36.dp)
                            )
                            Spacer(Modifier.width(12.dp))
                            Text(opcion.nombre,
                                fontFamily = MiFuenteFamilia)
                        }
                    }
                }
            }

            OutlinedTextField(
                value = numeroTarjeta,
                onValueChange = viewModel::actualizarNumeroTarjeta,
                label = {
                    Text(
                        "Número de Tarjeta",
                        fontFamily = MiFuenteFamilia
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )


            Spacer(Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = fechaCaducidad,
                    onValueChange = viewModel::actualizarFechaCaducidad,
                    label = { Text("Caducidad (MM/AA)",
                        fontFamily = MiFuenteFamilia) },
                    keyboardOptions = KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )

                Spacer(Modifier.width(8.dp))

                OutlinedTextField(
                    value = cvc,
                    onValueChange = viewModel::actualizarCvc,
                    label = { Text("CVC",
                        fontFamily = MiFuenteFamilia) },
                    keyboardOptions = KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.NumberPassword),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                BotonInicioPago(onClick = { onBotonInicioPagoPulsado("Inicio") })
                BotonResumenPago(onClick = { onBotonResumenPagoPulsado("Resumen Pago") })

            }


            }
        }
    }

@Composable
fun BotonInicioPago(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.btn_cancelar),
            fontFamily = MiFuenteFamilia
        )
    }
}
@Composable
fun BotonResumenPago(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.btn_aceptar),
            fontFamily = MiFuenteFamilia
        )
    }
}

