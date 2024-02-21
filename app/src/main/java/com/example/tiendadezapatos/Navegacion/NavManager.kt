package com.example.tiendadezapatos.Navegacion

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tiendadezapatos.ViewModels.LoginViewModel
import com.example.tiendadezapatos.Vistas.Inicio
import com.example.tiendadezapatos.Vistas.Login
import com.example.tiendadezapatos.Vistas.Register

@Composable
fun NavManager(loginVM: LoginViewModel){

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "Register"){
        composable ("Register"){
            Register(loginVM,navController)
        }
        composable("Login"){
            Login(loginVM,navController)
        }
        composable("Inicio"){
            Inicio(navController)
        }
    }

}