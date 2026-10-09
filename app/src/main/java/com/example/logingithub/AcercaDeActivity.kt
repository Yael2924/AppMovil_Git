
package com.example.logingithub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

    Scaffold(
        containerColor = Color(0xFFF3F6FC)
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TextButton(
                onClick = regresar,
                modifier = Modifier.align(Alignment.Start)
            ) {
                Text("← Regresar")
            }

            Spacer(modifier = Modifier.height(65.dp))

            Text(
                text = "Acerca de",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF152443)
            )

            Spacer(modifier = Modifier.height(25.dp))

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
        }
    }
}
