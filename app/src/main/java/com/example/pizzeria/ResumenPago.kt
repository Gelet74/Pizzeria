package com.example.pizzeria

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType.Companion.Uri
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pizzeria.ui.ViewModel.PizzeriaViewModel
import com.example.pizzeria.ui.theme.MiFuenteFamilia

@Composable
fun ResumenPago(
    viewModel: PizzeriaViewModel = viewModel(),
    onBotonAceptarResumenPagoPulsado: (String) -> Unit,
    onBotonEnviarPulsado: (String) -> Unit
) {
    val tipoPago by viewModel.tipoPagoSeleccionado.collectAsState()
    val numeroTarjeta by viewModel.numeroTarjeta.collectAsState()
    val fechaCaducidad by viewModel.fechaCaducidad.collectAsState()
    val cvc by viewModel.cvc.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    var mostrarDialogo by remember { mutableStateOf(false) }
    var enviarCorreo by remember { mutableStateOf(false) }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFDFD96))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Resumen del Pago",
                style = MaterialTheme.typography.headlineLarge,
                fontFamily = MiFuenteFamilia,
                modifier = Modifier.padding(bottom = 16.dp)
            )


            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Pedido:", style = MaterialTheme.typography.headlineSmall)
                    Text("Pizza: ${uiState.pizzaSeleccionada}", fontFamily = MiFuenteFamilia)
                    Text("Tamaño: ${uiState.tamanoSeleccionado}", fontFamily = MiFuenteFamilia)
                    Text("Cantidad pizzas: ${uiState.cantidadPizza}", fontFamily = MiFuenteFamilia)
                    Text("Bebida: ${uiState.bebidaSeleccionada}", fontFamily = MiFuenteFamilia)
                    Text("Cantidad bebidas: ${uiState.cantidadBebida}", fontFamily = MiFuenteFamilia)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))


            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Método de pago:", style = MaterialTheme.typography.headlineSmall)

                    if (tipoPago != null) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = tipoPago!!.iconoResId),
                                contentDescription = tipoPago!!.nombre,
                                modifier = Modifier
                                    .size(48.dp)
                                    .padding(end = 8.dp)
                            )
                            Text(tipoPago!!.nombre, fontFamily = MiFuenteFamilia)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))


                    val tarjetaOculta =
                        if (numeroTarjeta.length >= 4) "**** **** **** ${numeroTarjeta.takeLast(4)}"
                        else "**** **** **** ****"

                    Text("Número: $tarjetaOculta", fontFamily = MiFuenteFamilia)
                    Text("Caducidad: $fechaCaducidad", fontFamily = MiFuenteFamilia)
                    Text("CVC: ***", fontFamily = MiFuenteFamilia)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))


            Text(
                text = "TOTAL: ${"%.2f".format(uiState.precioTotal)} €",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                fontFamily = MiFuenteFamilia,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                BotonAceptarResumenPago(
                    onClick = {
                        mostrarDialogo = true
                    }
                )
                BotonEnviarPago(
                    onClick = { enviarCorreo = true }
                )
            }

           /* if (enviarCorreo) {
                Intent(Intent.ACTION_SEND).apply {
                    // The intent does not have a URI, so declare the "text/plain" MIME type
                    type = "text/plain"
                    putExtra(Intent.EXTRA_EMAIL, arrayOf("jan@example.com"))
                    putExtra(Intent.EXTRA_SUBJECT, "Email subject")
                    putExtra(Intent.EXTRA_TEXT, "Email message text")
                    putExtra(Intent.EXTRA_STREAM, Uri.parse("content://path/to/email/attachment"))
                    // You can also attach multiple items by passing an ArrayList of Uris
                }
                   AlertDialog(
                    onDismissRequest = { enviarCorreo= false },
                    title = { Text("Correo enviado", fontFamily = MiFuenteFamilia) },
                    text = { Text("Tu correo se ha enviado con éxito.",
                        fontFamily = MiFuenteFamilia) },
                    confirmButton = {
                        Button(onClick = {
                            mostrarDialogo = false
                            onBotonEnviarPagoPulsado("Inicio")
                        }) {
                            Text("Aceptar",
                                fontFamily = MiFuenteFamilia)
                        }
                    }
                )

            }*/


            if (mostrarDialogo) {
                AlertDialog(
                    onDismissRequest = { mostrarDialogo = false },
                    title = { Text("Pago realizado",
                        fontFamily = MiFuenteFamilia) },
                    text = { Text("Tu pago se ha completado con éxito.",
                        fontFamily = MiFuenteFamilia) },
                    confirmButton = {
                        Button(onClick = {
                            mostrarDialogo = false
                            onBotonAceptarResumenPagoPulsado("Inicio")
                        }) {
                            Text("Aceptar",
                                fontFamily = MiFuenteFamilia)
                        }
                    }
                )
            }
        }
    }
}
@Composable
fun BotonEnviarPago(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.btn_enviar),
            fontFamily = MiFuenteFamilia
        )
    }
}
@Composable
fun BotonAceptarResumenPago(
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


