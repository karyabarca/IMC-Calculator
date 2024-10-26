package com.ebc.imccalculator.view

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ebc.imccalculator.R

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    // Cargar la animación
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.animation1))
    val progress by animateLottieCompositionAsState(composition)

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LottieAnimation(
            composition,
            { progress },
            modifier = Modifier.size(300.dp)
        )
    }
    // Navegación después de que la animación termine y delay
    LaunchedEffect(key1 = progress) {
        if (progress == 1f) {
            delay(100)
            onTimeout()
        }
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    SplashScreen(onTimeout = {})
}
