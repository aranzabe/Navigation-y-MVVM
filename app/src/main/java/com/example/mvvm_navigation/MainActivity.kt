package com.example.mvvm_navigation

import Login
import Rutas.Login
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.mvvm_navigation.ui.theme.MVVM_NavigationTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mvvm_navigation.Ventana2.Pant2
import com.example.mvvm_navigation.ViewModel.LoginViewModel

class MainActivity : ComponentActivity() {
    private var lvm = LoginViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MVVM_NavigationTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = Rutas.Login){
                    composable(Rutas.Login){
                        Login(navController, lvm)
                    }
                    composable(Rutas.Pantalla2){
                        Pant2(navController, lvm)
                    }
                }
            }
        }
    }
}

