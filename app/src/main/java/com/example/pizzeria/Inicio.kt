package com.example.pizzeria

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pizzeria.ui.theme.MiFuenteFamilia
import com.example.pizzeria.ui.theme.PizzeriaTheme



@Composable
fun Inicio(
    onBotonSiguientePulsado: (String) -> Unit,
    onBotonResumenPulsado: (String) -> Unit,
    modifier: Modifier = Modifier

) {
    val imagen = painterResource(R.drawable.logo_pizza)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color(0xFFFDFD96))
    ) {
        Image(
            painter = imagen,
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            alpha = 0.8f,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .padding(top = 40.dp)
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(text = stringResource(R.string.label_nombre) + " " + stringResource(R.string.nombre))
                        Text(text = stringResource(R.string.label_correo) + " " + stringResource(R.string.correo))
                        Text(text = stringResource(R.string.label_tfno) + " " + stringResource(R.string.tfno))
                    }
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(id = R.drawable.imagen1),
                        contentDescription = null
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(bottom=16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                BotonSiguiente(onClick = { onBotonSiguientePulsado("Hacer Pedido") })
                BotonResumen(onClick = { onBotonResumenPulsado("Ver Pedidos") })
            }
        }
    }
}

@Composable
fun BotonSiguiente(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.hacer_pedido),
            fontFamily = MiFuenteFamilia
        )
    }
}
@Composable
fun BotonResumen(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.ver_pedidos),
            fontFamily = MiFuenteFamilia
        )
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PizzeriaTheme {
        Inicio(onBotonSiguientePulsado = {},
                onBotonResumenPulsado = {})
    }
}