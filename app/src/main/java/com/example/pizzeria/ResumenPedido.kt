package com.example.pizzeria

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pizzeria.modelo.PizzeriaUIState
import com.example.pizzeria.ui.ViewModel.PizzeriaViewModel
import androidx.compose.ui.graphics.Color

@Composable
fun ResumenPedido(
    viewModel: PizzeriaViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(R.string.resumen_title),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Pizza: ${uiState.pizzaSeleccionada}")
                Text("Tamaño: ${uiState.tamanoSeleccionado}")
                Text("Cantidad: ${uiState.cantidadPizza}")
            }
        }

        Spacer(Modifier.height(16.dp))

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Bebida: ${uiState.bebidaSeleccionada}")
                Text("Cantidad: ${uiState.cantidadBebida}")
            }
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = "TOTAL: ${"%.2f".format(uiState.precioTotal)} €",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(Modifier.height(32.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(onClick = {  }) {
                Text(stringResource(R.string.btn_cancelar))
            }
            Button(onClick = { }) {
                Text(stringResource(R.string.pago_form_title))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ResumenPedidoPreview() {
    Surface(color = Color(0xFFFFBB30)) {
        ResumenPedido()
    }
}
