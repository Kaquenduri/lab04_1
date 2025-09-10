package com.example.lab04

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab04.ui.theme.Lab04Theme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InterfazSimple()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun InterfazSimple() {
    // Estado para la categoría seleccionada
    var categoriaSeleccionada by remember { mutableStateOf("Secundaria") }
    var mostrarDialogo by remember { mutableStateOf(false) }
    val estadoSnackbar = remember { SnackbarHostState() }
    val alcanceCorrutina = rememberCoroutineScope()
    
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // AssistChip para alternar categorías
            AssistChip(
                onClick = {
                    categoriaSeleccionada = if (categoriaSeleccionada == "Secundaria")
                        "Primaria"
                    else
                        "Secundaria"
                },
                label = { Text("Categoría: $categoriaSeleccionada") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Card con ícono
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Cyan),
                onClick = { mostrarDialogo = true }
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Icono de Curso",
                        tint = Color.Blue
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Curso de Móviles")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para mostrar Snackbar
            Button(onClick = {
                alcanceCorrutina.launch {
                    estadoSnackbar.showSnackbar("Te inscribiste en $categoriaSeleccionada")
                }
            }) {
                Text("Inscribirme")
            }

        }
        // Diálogo de confirmación
        if (mostrarDialogo) {
            AlertDialog(
                onDismissRequest = { mostrarDialogo = false },
                title = { Text("Confirmar inscripción") },
                text = { Text("¿Quieres inscribirte en $categoriaSeleccionada?") },
                confirmButton = {
                    TextButton(onClick = { mostrarDialogo = false }) {
                        Text("Sí")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { mostrarDialogo = false }) {
                        Text("No")
                    }
                }
            )
        }
    }
}







