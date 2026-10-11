package com.example.logingithub

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.logingithub.ui.theme.LoginGitHubTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    onSplashFinished: () -> Unit,
    isTestingOrPreview: Boolean = false,
) {
    // Escala y opacidad animadas
    val scale = remember { Animatable(if (isTestingOrPreview) 1f else 0.4f) }
    val alpha = remember { Animatable(if (isTestingOrPreview) 1f else 0f) }

    // Animación continua de aureola resplandeciente
    val infiniteTransition = rememberInfiniteTransition(label = "SplashPulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "PulseScale"
    )

    if (!isTestingOrPreview) {
        LaunchedEffect(Unit) {
            launch {
                scale.animateTo(
                    targetValue = 1f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
            }
            launch {
                alpha.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = 700)
                )
            }

            // Duración del splash antes de pasar al login
            delay(2200)
            onSplashFinished()
        }
    }

    val fondoGradiente = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF0F172A), // Azul noche
            Color(0xFF1E1B4B), // Violeta oscuro
            Color(0xFF0F172A)
        )
    )

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(fondoGradiente),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(24.dp)
            ) {

                // Logo principal con aureola y degradado
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    // Resplandor difuminado
                    Box(
                        modifier = Modifier
                            .size(140.dp)
                            .scale(pulseScale)
                            .alpha(alpha.value * 0.35f)
                            .clip(CircleShape)
                            .background(
                                Brush.radialGradient(
                                    colors = listOf(
                                        Color(0xFF355CE7),
                                        Color(0xFF7C3AED),
                                        Color.Transparent
                                    )
                                )
                            )
                    )

                    // Contenedor del ícono de GitHub
                    Box(
                        modifier = Modifier
                            .size(108.dp)
                            .scale(scale.value)
                            .alpha(alpha.value)
                            .clip(RoundedCornerShape(32.dp))
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFF1E293B),
                                        Color(0xFF0F172A)
                                    )
                                )
                            )
                            .border(
                                width = 1.5.dp,
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFF355CE7),
                                        Color(0xFF818CF8)
                                    )
                                ),
                                shape = RoundedCornerShape(32.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_github),
                            contentDescription = "GitHub Logo",
                            modifier = Modifier.size(52.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(36.dp))

                // Textos informativos
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .scale(scale.value)
                        .alpha(alpha.value)
                ) {
                    Text(
                        text = "LoginGitHub",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        letterSpacing = 1.2.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Autenticación rápida y segura con GitHub",
                        fontSize = 14.sp,
                        color = Color(0xFFCBD5E1),
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(52.dp))

                // Indicador de carga elegante
                Box(
                    modifier = Modifier.alpha(alpha.value)
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(26.dp),
                        color = Color(0xFF818CF8),
                        strokeWidth = 2.5.dp
                    )
                }
            }

            // Versión de la app
            Text(
                text = "LoginGitHub · v1.0",
                fontSize = 12.sp,
                color = Color(0xFF64748B),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 36.dp)
                    .alpha(alpha.value)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashPreview() {
    LoginGitHubTheme {
        SplashScreen(
            onSplashFinished = {},
            isTestingOrPreview = true
        )
    }
}
