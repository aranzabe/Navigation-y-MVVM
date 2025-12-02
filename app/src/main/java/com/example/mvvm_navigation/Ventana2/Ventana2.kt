package com.example.mvvm_navigation.Ventana2

import Modelo.Usuario
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.mvvm_navigation.ViewModel.LoginViewModel

@Composable
fun Pant2(navController: NavHostController, loginViewModel: LoginViewModel) {
    val almacenPersonas by loginViewModel.usuarios.observeAsState(ArrayList<Usuario>())
    Column(modifier = Modifier
        .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
        ){
        Text(text = "Hola esto es la pantalla 2")
        //Text(text = "Valor del arrayList: ${almacenPersonas.toString()}")
        LazyColumn {
            items(almacenPersonas.size) {
                Text(text = almacenPersonas[it].email + " - " + almacenPersonas[it].estadoCheck.toString() + "")
            }
        }
        Button(onClick = {
            navController.navigate(Rutas.Login)
        }) {
            Text(text = "Volver")
        }
    }
}