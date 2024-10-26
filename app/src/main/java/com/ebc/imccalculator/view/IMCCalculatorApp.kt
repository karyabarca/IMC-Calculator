package com.ebc.imccalculator.view

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.*
import com.ebc.imccalculator.R

@Composable
fun IMCCalculatorApp() {
    var peso by remember { mutableStateOf("") }
    var altura by remember { mutableStateOf("") }
    var imc by remember { mutableStateOf(0.0) }
    var isMetric by remember { mutableStateOf(true) } // Estado del Switch

    // Contexto y FocusManager para ocultar el teclado
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Animación Lottie
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.animation2))
        LottieAnimation(
            composition,
            modifier = Modifier.size(150.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Texto para indicar el tipo de medida
        Text(
            text = if (isMetric) "Sistema Métrico (kg/m)" else "Sistema Imperial (lbs/pulgadas)",
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Switch para alternar entre Sistema Métrico e Imperial
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Usar Sistema Métrico")
            Spacer(modifier = Modifier.width(8.dp))
            Switch(
                checked = isMetric,
                onCheckedChange = { isMetric = it }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Campos de entrada para peso y altura
        TextField(
            value = peso,
            onValueChange = { peso = it },
            label = { Text(if (isMetric) "Peso (kg)" else "Peso (lbs)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = androidx.compose.ui.text.input.KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    // Ocultar el teclado cuando se presiona "Done"
                    hideKeyboard(context)
                    focusManager.clearFocus()
                }
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = altura,
            onValueChange = { altura = it },
            label = { Text(if (isMetric) "Altura (m)" else "Altura (pulgadas)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = androidx.compose.ui.text.input.KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    // Ocultar teclado cuando se presiona "Done"
                    hideKeyboard(context)
                    focusManager.clearFocus()
                }
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para calcular el IMC
        Button(onClick = {
            val pesoValue = peso.toDoubleOrNull()
            val alturaValue = altura.toDoubleOrNull()

            // Ocultar el teclado cuando se presiona el botón
            hideKeyboard(context)
            focusManager.clearFocus()

            if (pesoValue != null && alturaValue != null && alturaValue > 0) {
                imc = if (isMetric) {
                    // Cálculo del IMC en sistema métrico
                    pesoValue / (alturaValue * alturaValue)
                } else {
                    // Cálculo del IMC en sistema imperial
                    (pesoValue / (alturaValue * alturaValue)) * 703
                }
            }
        }) {
            Text("Calcular IMC")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar el resultado del IMC
        Text(
            text = if (imc > 0) "Tu IMC es: ${"%.2f".format(imc)}" else "",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

// Función para ocultar el teclado
fun hideKeyboard(context: Context) {
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    val windowToken = (context as? Activity)?.currentFocus?.windowToken
    windowToken?.let {
        inputMethodManager.hideSoftInputFromWindow(it, 0)
    }
}

@Preview(showBackground = true)
@Composable
fun IMCCalculatorPreview() {
    IMCCalculatorApp()
}
