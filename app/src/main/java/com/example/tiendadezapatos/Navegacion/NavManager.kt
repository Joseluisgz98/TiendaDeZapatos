package com.example.tiendadezapatos.Navegacion

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tiendadezapatos.ViewModels.FavoritoViewModel
import com.example.tiendadezapatos.ViewModels.LoginViewModel
import com.example.tiendadezapatos.ViewModels.ZapatosViewModel
import com.example.tiendadezapatos.Vistas.Favorito
import com.example.tiendadezapatos.Vistas.Inicio
import com.example.tiendadezapatos.Vistas.Login
import com.example.tiendadezapatos.Vistas.Register
import com.example.tiendadezapatos.Vistas.Tienda

@Composable
fun NavManager(loginVM: LoginViewModel,zapatosVM: ZapatosViewModel,favoritoVM: FavoritoViewModel){

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "Login"){
        composable ("Register"){
            Register(loginVM,navController)
        }
        composable("Login"){
            Login(loginVM,navController)
        }
        composable("Inicio"){
            Inicio(navController,zapatosVM)
        }
        composable("Favorite"){
            Favorito(navController)
        }
        composable("Shop"){
            Tienda(navController)
        }
    }

}