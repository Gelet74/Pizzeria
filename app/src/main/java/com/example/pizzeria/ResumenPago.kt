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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pizzeria.modelo.PizzeriaUIState
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
    val context = LocalContext.current

    var mostrarDialogo by remember { mutableStateOf(false) }
    var mostrarDialogoCorreo by remember { mutableStateOf(false) }

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
                text = stringResource(R.string.resumen_pago),
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
                    Text(
                        stringResource(R.string.pedido_resumen),
                        fontFamily = MiFuenteFamilia,
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Text(
                        text = "Pizza: ${
                            when (uiState.pizzaSeleccionada?.lowercase()) {
                                "romana" -> stringResource(R.string.pizza1)
                                "barbacoa" -> stringResource(R.string.pizza2)
                                "margarita" -> stringResource(R.string.pizza3)
                                else -> uiState.pizzaSeleccionada ?: ""
                            }
                        }",
                        fontFamily = MiFuenteFamilia
                    )
                    Text(
                        "${stringResource(R.string.label_tamano)} ${
                            when (uiState.tamanoSeleccionado?.lowercase()) {
                                "pequeña" -> stringResource(R.string.tamano_pequena)
                                "mediana" -> stringResource(R.string.tamano_mediana)
                                "grande" -> stringResource(R.string.tamano_grande)
                                else -> uiState.tamanoSeleccionado ?: ""
                            }
                        }",
                        fontFamily = MiFuenteFamilia
                    )
                    Text(
                        "${stringResource(R.string.label_cantidad)} ${uiState.cantidadPizza}",
                        fontFamily = MiFuenteFamilia
                    )
                    Text(
                        "${stringResource(R.string.bebida)}${
                            when (uiState.bebidaSeleccionada?.lowercase()) {
                                "agua" -> stringResource(R.string.bebida1)
                                "cola" -> stringResource(R.string.bebida2)
                                "sin bebida" -> stringResource(R.string.bebida3)
                                else -> uiState.bebidaSeleccionada ?: " "
                            }
                        }",
                        fontFamily = MiFuenteFamilia
                    )
                    Text(
                        "${stringResource(R.string.label_cantidad_bebidas)} ${uiState.cantidadBebida}",
                        fontFamily = MiFuenteFamilia
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        stringResource(R.string.metodo_pago),
                        fontFamily = MiFuenteFamilia,
                        style = MaterialTheme.typography.headlineSmall
                    )

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

                    Text(
                        "${stringResource(R.string.numero)}$tarjetaOculta",
                        fontFamily = MiFuenteFamilia
                    )

                    Text(
                        "${stringResource(R.string.caducidad_resumen)} $fechaCaducidad",
                        fontFamily = MiFuenteFamilia
                    )
                    Text(
                        "${stringResource(R.string.cvc)} : ***",
                        fontFamily = MiFuenteFamilia
                    )
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
                        viewModel.registrarPedidoActual()
                        mostrarDialogo = true
                    }
                )
                BotonEnviarPago(
                    onClick = {
                        viewModel.registrarPedidoActual()
                        enviarCorreo(context, uiState)
                    }
                )
            }


            if (mostrarDialogo) {
                AlertDialog(
                    onDismissRequest = { mostrarDialogo = false },
                    title = {
                        Text(
                            stringResource(R.string.pago_hecho),
                            fontFamily = MiFuenteFamilia
                        )
                    },
                    text = {
                        Text(
                            stringResource(R.string.pago_exito),
                            fontFamily = MiFuenteFamilia
                        )
                    },
                    confirmButton = {
                        Button(onClick = {
                            mostrarDialogo = false
                            onBotonAceptarResumenPagoPulsado("Inicio")
                        }) {
                            Text(
                                stringResource(R.string.btn_aceptar),
                                fontFamily = MiFuenteFamilia
                            )
                        }
                    }
                )
            }

            if (mostrarDialogoCorreo) {
                AlertDialog(
                    onDismissRequest = { mostrarDialogoCorreo = false },
                    title = {
                        Text(
                            "Correo enviado",
                            fontFamily = MiFuenteFamilia
                        )
                    },
                    text = {
                        Text(
                            "Tu pedido se ha enviado por correo con éxito.",
                            fontFamily = MiFuenteFamilia
                        )
                    },
                    confirmButton = {
                        Button(onClick = {
                            mostrarDialogoCorreo = false
                            onBotonEnviarPulsado("Inicio")
                        }) {
                            Text(
                                "Aceptar",
                                fontFamily = MiFuenteFamilia
                            )
                        }
                    }
                )
            }
        }
    }
}

private fun enviarCorreo(context: android.content.Context, uiState: PizzeriaUIState) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "message/rfc822"
        putExtra(Intent.EXTRA_EMAIL, arrayOf("cliente@ejemplo.com"))
        putExtra(Intent.EXTRA_SUBJECT, "Confirmación de Pedido - Pizzería")

    }

    try {
        context.startActivity(Intent.createChooser(intent, "Enviar correo..."))

    } catch (e: Exception) {
        android.widget.Toast.makeText(
            context,
            "No se encontró una aplicación de correo",
            android.widget.Toast.LENGTH_LONG
        ).show()
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