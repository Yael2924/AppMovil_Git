
package com.example.logingithub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.logingithub.ui.theme.LoginGitHubTheme

class AcercaDeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            LoginGitHubTheme {
                PantallaAcercaDe(
                    regresar = { finish() }
                )
            }
        }
    }
}

@Composable
fun PantallaAcercaDe(regresar: () -> Unit) {

    val estudiantes = listOf(
        "Cruz López Ángel de Jesús",
        "De la Cruz Cruz Saúl Yael",
        "Gómez Sánchez Juan de Jesús",
        "Santiago Sánchez Jehovani"
    )

    var estudianteSeleccionado by remember {
        mutableStateOf<String?>(null)
    }

    val esPerfilPersonalizado =
        estudianteSeleccionado == "De la Cruz Cruz Saúl Yael" ||
                estudianteSeleccionado == "Gómez Sánchez Juan de Jesús"

    val esJuan =
        estudianteSeleccionado == "Gómez Sánchez Juan de Jesús"

    Scaffold(
        containerColor = Color(0xFFF3F6FC)
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TextButton(
                onClick = {
                    if (estudianteSeleccionado != null) {
                        estudianteSeleccionado = null
                    } else {
                        regresar()
                    }
                },
                modifier = Modifier.align(Alignment.Start)
            ) {
                Text(
                    text = "← Regresar",
                    color = Color(0xFF355CE7)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Acerca de",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF152443)
            )

            Spacer(modifier = Modifier.height(25.dp))

            if (estudianteSeleccionado == null) {

                // Tarjetas con los nombres de los estudiantes
                estudiantes.forEach { estudiante ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 14.dp)
                            .clickable {
                                estudianteSeleccionado = estudiante
                            },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 4.dp
                        )
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(22.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = estudiante,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF152443),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Tarjeta original de LoginGitHub
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(25.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "LoginGitHub",
                            fontSize = 23.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF355CE7)
                        )

                        Spacer(modifier = Modifier.height(15.dp))

                        Text(
                            text = "Aplicación desarrollada con Kotlin y Jetpack Compose.",
                            fontSize = 15.sp,
                            textAlign = TextAlign.Center,
                            lineHeight = 23.sp,
                            color = Color.Gray
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = "Versión 1.0",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                }

            } else {

                // Tarjeta de información del estudiante
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 6.dp
                    )
                ) {

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        if (esPerfilPersonalizado) {

                            // Encabezado del perfil
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(0xFF152443))
                                    .padding(vertical = 24.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "MI PERFIL",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    letterSpacing = 2.sp
                                )
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            // Fotografía personal de Saúl o Juan
                            Image(
                                painter = painterResource(
                                    id = if (esJuan) {
                                        R.drawable.juan
                                    } else {
                                        R.drawable.saul
                                    }
                                ),
                                contentDescription = if (esJuan) {
                                    "Foto de Juan de Jesús Gómez Sánchez"
                                } else {
                                    "Foto de Saúl Yael de la Cruz Cruz"
                                },
                                modifier = Modifier
                                    .size(145.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = if (esJuan) {
                                    "Juan de Jesús Gómez Sánchez"
                                } else {
                                    "Saúl Yael de la Cruz Cruz"
                                },
                                modifier = Modifier.padding(horizontal = 16.dp),
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF152443),
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Estudiante de Ingeniería de Software",
                                modifier = Modifier.padding(horizontal = 16.dp),
                                fontSize = 14.sp,
                                color = Color(0xFF355CE7),
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                        } else {

                            Spacer(modifier = Modifier.height(24.dp))

                            Text(
                                text = estudianteSeleccionado ?: "",
                                modifier = Modifier.padding(horizontal = 16.dp),
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF152443),
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(24.dp))
                        }

                        // Información académica
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp)
                                .padding(bottom = 26.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            HorizontalDivider(
                                color = Color(0xFFD9E1F2)
                            )

                            Spacer(modifier = Modifier.height(22.dp))

                            Text(
                                text = "UNIVERSIDAD TECNOLÓGICA\nDE LA SELVA",
                                modifier = Modifier.fillMaxWidth(),
                                fontSize = 19.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF355CE7),
                                textAlign = TextAlign.Center,
                                lineHeight = 25.sp
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            Text(
                                text = "Ing. en Desarrollo y Gestión de Software",
                                modifier = Modifier.fillMaxWidth(),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF152443),
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = "Desarrollo Móvil Integral",
                                fontSize = 16.sp,
                                color = Color(0xFF152443),
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = "Grado: 10  |  Grupo: A",
                                fontSize = 15.sp,
                                color = Color(0xFF152443),
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = "Docente: Armando Méndez Morales",
                                modifier = Modifier.fillMaxWidth(),
                                fontSize = 15.sp,
                                color = Color(0xFF152443),
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            HorizontalDivider(
                                color = Color(0xFFD9E1F2)
                            )

                            Spacer(modifier = Modifier.height(22.dp))

                            Text(
                                text = "ACERCA DE MÍ",
                                modifier = Modifier.fillMaxWidth(),
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF355CE7)
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = "$estudianteSeleccionado, " +
                                        "estudiante de la Universidad Tecnológica " +
                                        "de la Selva, cursando el 10 cuatrimestre " +
                                        "del grupo A de la carrera Ing. en " +
                                        "Desarrollo y Gestión de Software.",
                                modifier = Modifier.fillMaxWidth(),
                                fontSize = 15.sp,
                                color = Color(0xFF333333),
                                textAlign = TextAlign.Justify,
                                lineHeight = 24.sp
                            )
                        }
                    }
                }
            }
        }
    }
}