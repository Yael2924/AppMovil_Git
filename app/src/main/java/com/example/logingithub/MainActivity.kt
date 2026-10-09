
package com.example.logingithub

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.logingithub.ui.theme.LoginGitHubTheme

private val Azul = Color(0xFF355CE7)
private val Fondo = Color(0xFFF3F6FC)
private val Texto = Color(0xFF152443)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            LoginGitHubTheme {
                MenuPrincipal(
                    abrirAcercaDe = {
                        startActivity(
                            Intent(
                                this,
                                AcercaDeActivity::class.java
                            )
                        )
                    },
                    cerrarSesion = {
                        // Temporalmente cierra la pantalla.
                        // Después conectaremos el logout real.
                        finish()
                    }
                )
            }
        }
    }
}

@Composable
fun MenuPrincipal(
    abrirAcercaDe: () -> Unit,
    cerrarSesion: () -> Unit
) {
    var mostrarDialogo by remember {
        mutableStateOf(false)
    }

    Scaffold(
        containerColor = Fondo
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(22.dp)
        ) {

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "LoginGitHub",
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold,
                color = Texto
            )

            Spacer(modifier = Modifier.height(55.dp))

            Text(
                text = "MENÚ PRINCIPAL",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Azul
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "¡Bienvenido!",
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                color = Texto
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Nos alegra tenerte aquí. Explora las opciones disponibles.",
                fontSize = 15.sp,
                lineHeight = 23.sp,
                color = Color(0xFF71809B)
            )

            Spacer(modifier = Modifier.height(30.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Azul
                )
            ) {
                Column(
                    modifier = Modifier.padding(25.dp)
                ) {
                    Text(
                        text = "✦",
                        fontSize = 30.sp,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Todo listo para comenzar",
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Accede a las funciones y conoce más sobre nuestra aplicación.",
                        fontSize = 14.sp,
                        lineHeight = 21.sp,
                        color = Color(0xFFE1E8FF)
                    )
                }
            }

            Spacer(modifier = Modifier.height(35.dp))

            Text(
                text = "Opciones",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = Texto
            )

            Spacer(modifier = Modifier.height(16.dp))

            OpcionMenu(
                titulo = "Acerca de",
                descripcion = "Información de la aplicación",
                simbolo = "ⓘ",
                colorFondo = Color.White,
                colorTexto = Texto,
                alPresionar = abrirAcercaDe
            )

            Spacer(modifier = Modifier.height(14.dp))

            OpcionMenu(
                titulo = "Cerrar sesión",
                descripcion = "Salir de tu cuenta",
                simbolo = "↪",
                colorFondo = Color(0xFFFFEEEE),
                colorTexto = Color(0xFFC03945),
                alPresionar = {
                    mostrarDialogo = true
                }
            )

            Spacer(modifier = Modifier.height(55.dp))

            Text(
                text = "LoginGitHub · Versión 1.0",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }

    if (mostrarDialogo) {
        AlertDialog(
            onDismissRequest = {
                mostrarDialogo = false
            },
            title = {
                Text("Cerrar sesión")
            },
            text = {
                Text(
                    "¿Estás seguro de que deseas salir de tu cuenta?"
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        mostrarDialogo = false
                        cerrarSesion()
                    }
                ) {
                    Text(
                        text = "Cerrar sesión",
                        color = Color(0xFFC03945)
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        mostrarDialogo = false
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun OpcionMenu(
    titulo: String,
    descripcion: String,
    simbolo: String,
    colorFondo: Color,
    colorTexto: Color,
    alPresionar: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { alPresionar() },
        shape = RoundedCornerShape(17.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorFondo
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(19.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = simbolo,
                fontSize = 27.sp,
                color = colorTexto
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = titulo,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorTexto
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = descripcion,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Text(
                text = "›",
                fontSize = 27.sp,
                color = colorTexto
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MenuPreview() {
    LoginGitHubTheme {
        MenuPrincipal(
            abrirAcercaDe = {},
            cerrarSesion = {}
        )
    }
}
