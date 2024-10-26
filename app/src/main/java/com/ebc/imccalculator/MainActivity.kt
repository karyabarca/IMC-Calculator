package com.ebc.imccalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ebc.imccalculator.ui.theme.IMCCalculatorTheme
import com.ebc.imccalculator.view.IMCCalculatorApp
import com.ebc.imccalculator.view.SplashScreen
import com.ebc.imccalculator.view.WelcomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IMCCalculatorTheme {
                AppNavigator()
            }
        }
    }
}

@Composable
fun AppNavigator() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash") {
        // Pantalla Splash
        composable("splash") {
            SplashScreen(onTimeout = {
                navController.navigate("welcome") {
                    popUpTo("splash") { inclusive = true }
                }
            })
        }

        // Pantalla de bienvenida
        composable("welcome") {
            WelcomeScreen(onStartClick = {
                navController.navigate("home") {
                    popUpTo("welcome") { inclusive = true }
                }
            })
        }

        // Pantalla principal (Calculadora de IMC)
        composable("home") {
            IMCCalculatorApp()
        }
    }
}
