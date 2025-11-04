package com.example.pizzeria

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.pizzeria.ui.theme.PizzeriaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PizzeriaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    //Inicio(
                    // modifier = Modifier.padding(innerPadding)
                    //)
                    PizzeriaApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}