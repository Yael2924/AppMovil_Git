
package com.example.logingithub

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen(
    isLoading: Boolean,
    message: String,
    userCode: String?,
    onGitHubClick: () -> Unit,
    onOpenVerification: () -> Unit
) {
    val oscuro = Color(0xFF24292F)
    val azul = Color(0xFF355CE7)
    val fondo = Color(0xFFF3F6FC)

    var usuario by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var mensajeLocal by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = fondo
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "LoginGitHub",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = oscuro
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Inicia sesión en tu cuenta",
                color = Color.DarkGray
            )

            Spacer(Modifier.height(28.dp))

            
            OutlinedTextField(
                value = usuario,
                onValueChange = {
                    usuario = it
                    mensajeLocal = ""
                },
                label = {
                    Text("Usuario o correo", color = Color.DarkGray)
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.DarkGray,
                    cursorColor = Color.Black
                )
            )

            Spacer(Modifier.height(14.dp))

            OutlinedTextField(
                value = contrasena,
                onValueChange = {
                    contrasena = it
                    mensajeLocal = ""
                },
                label = {
                    Text("Contraseña", color = Color.DarkGray)
                },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.DarkGray,
                    cursorColor = Color.Black
                )
            )

            Spacer(Modifier.height(20.dp))

            Button(
                onClick = {
                    mensajeLocal =
                        "Este inicio de sesión es solo visual."
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = azul
                )
            ) {
                Text(
                    text = "Iniciar sesión",
                    color = Color.White
                )
            }

            if (mensajeLocal.isNotBlank()) {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = mensajeLocal,
                    color = Color.DarkGray,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(Modifier.height(20.dp))

            Button(
                onClick = onGitHubClick,
                enabled = !isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = oscuro
                )
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                    Spacer(Modifier.size(10.dp))
                    Text("Conectando con GitHub...")
                } else {
                    Row(
                        verticalAlignment =
                            Alignment.CenterVertically,
                        horizontalArrangement =
                            Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(
                                id = R.drawable.ic_github
                            ),
                            contentDescription = "GitHub",
                            modifier = Modifier.size(23.dp)
                        )

                        Spacer(Modifier.size(10.dp))

                        Text(
                            text = "Continuar con GitHub",
                            color = Color.White
                        )
                    }
                }
            }

            if (userCode != null) {
                Spacer(Modifier.height(20.dp))

                Text(
                    text = "Código de autorización",
                    fontWeight = FontWeight.Bold,
                    color = oscuro
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = userCode,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = azul
                )

                TextButton(
                    onClick = onOpenVerification
                ) {
                    Text("Abrir página de GitHub")
                }
            }

            if (message.isNotBlank()) {
                Spacer(Modifier.height(12.dp))

                Text(
                    text = message,
                    textAlign = TextAlign.Center,
                    color = if (message.startsWith("Error:")) {
                        Color(0xFFC03945)
                    } else {
                        oscuro
                    }
                )
            }
        }
    }
}
