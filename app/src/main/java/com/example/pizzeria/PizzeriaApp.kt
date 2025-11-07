package com.example.pizzeria

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pizzeria.ui.ViewModel.PizzeriaViewModel

// ---------- ENUM DE PANTALLAS ----------
enum class Pantallas(@StringRes val titulo: Int) {
    Inicio(titulo = R.string.app_name),
    HacerPedido(titulo = R.string.Haz_pedido),
    ResumenPedido(titulo = R.string.resumen_pedido),
    FormularioPago(titulo = R.string.formulario_pago),
    ResumenPago(titulo = R.string.resumen_pago),
    VerPedidos(titulo = R.string.ver_pedidos)
}

// ---------- APLICACIÓN PRINCIPAL ----------
@Composable
fun PizzeriaApp(
    viewModel: PizzeriaViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    val pilaRetroceso by navController.currentBackStackEntryAsState()
    val pantallaActual = Pantallas.valueOf(
        pilaRetroceso?.destination?.route ?: Pantallas.Inicio.name
    )

    Scaffold(
        topBar = {
            AppTopBar(
                pantallaActual = pantallaActual,
                puedeNavegarAtras = navController.previousBackStackEntry != null,
                onNavegarAtras = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->

        val uiState by viewModel.uiState.collectAsState()

        // --- NAVHOST ---
        NavHost(
            navController = navController,
            startDestination = Pantallas.Inicio.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            // -------- PANTALLA INICIO --------
            composable(Pantallas.Inicio.name) {
                Inicio(
                    onBotonSiguientePulsado = {
                        navController.navigate(Pantallas.HacerPedido.name)
                    },
                    onBotonResumenPulsado = {
                       navController.navigate(Pantallas.VerPedidos.name)
                    }
                )
            }

            // -------- PANTALLA HACER PEDIDO --------
            composable(Pantallas.HacerPedido.name) {
                HacerPedido(
                    viewModel = viewModel,
                    onBotonResumenPedidoPulsado =  {
                        navController.navigate(Pantallas.ResumenPedido.name)
                    },
                    onBotonInicioPulsado = {
                        navController.popBackStack(Pantallas.Inicio.name, inclusive = false)
                    }
                )
            }
            // -------- PANTALLA FORMULARIO DE PAGO--------
            composable(Pantallas.FormularioPago.name) {
                FormularioPago(
                    viewModel = viewModel,
                    onBotonResumenPagoPulsado =  {
                        navController.navigate(Pantallas.ResumenPago.name)
                    },
                    onBotonInicioPagoPulsado = {
                        navController.popBackStack(Pantallas.Inicio.name, inclusive = false)
                    }
                )
            }
            // -------- PANTALLA VER PEDIDOS --------
            composable(Pantallas.VerPedidos.name) {
                Verpedidos(
                    viewModel = viewModel,
                    onBotonInicioVerPedidosPulsado = {
                        navController.popBackStack(Pantallas.Inicio.name, inclusive = false)
                    }
                )
            }
            // -------- PANTALLA RESUMEN PEDIDO --------
            composable(Pantallas.ResumenPedido.name) {
                ResumenPedido(
                    viewModel = viewModel,
                    onBotonCancelarPulsado =  {
                        navController.navigate(Pantallas.HacerPedido.name)
                    },
                    onBotonPagoPulsado = {
                        navController.navigate(Pantallas.FormularioPago.name)
                    }
                )
            }
            // -------- PANTALLA RESUMEN PAGO --------
            composable(Pantallas.ResumenPago.name) {
                ResumenPago(
                    viewModel = viewModel,
                    onBotonAceptarResumenPagoPulsado =  {
                        navController.popBackStack(Pantallas.Inicio.name, inclusive = false)
                    },
                    onBotonEnviarPulsado = {
                        navController.popBackStack(Pantallas.Inicio.name, inclusive = false)
                    }
                )


            }
        }
    }
}
// ---------- TOP BAR ----------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    pantallaActual: Pantallas,
    puedeNavegarAtras: Boolean,
    onNavegarAtras: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(text = stringResource(id = pantallaActual.titulo)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        navigationIcon = {
            if (puedeNavegarAtras) {
                IconButton(onClick = onNavegarAtras) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.atras)
                    )
                }
            }
        },
        modifier = Modifier.height(80.dp)
    )
}