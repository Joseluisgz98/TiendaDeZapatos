package com.example.tiendadezapatos.Navegacion

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tiendadezapatos.ViewModels.CompraViewModel
import com.example.tiendadezapatos.ViewModels.FavoritoViewModel
import com.example.tiendadezapatos.ViewModels.LoginViewModel
import com.example.tiendadezapatos.ViewModels.ZapatosViewModel
import com.example.tiendadezapatos.Vistas.Admin
import com.example.tiendadezapatos.Vistas.Favorito
import com.example.tiendadezapatos.Vistas.Inicio
import com.example.tiendadezapatos.Vistas.Login
import com.example.tiendadezapatos.Vistas.Perfil
import com.example.tiendadezapatos.Vistas.Register
import com.example.tiendadezapatos.Vistas.Tienda

@Composable
fun NavManager(loginVM: LoginViewModel,zapatosVM: ZapatosViewModel,favoritoVM: FavoritoViewModel,compraViewModel: CompraViewModel){

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "Login"){
        composable ("Register"){
            Register(loginVM,navController)
        }
        composable("Login"){
            if(loginVM.estaLogin()){
                Perfil(navController,loginVM)
            }else{
                Login(loginVM,navController)
            }
        }
        composable("Inicio"){
            if (loginVM.esAdmin()) {
                // Si el usuario es un administrador, navegamos a la vista del administrador
                Admin(navController)
            } else {
                // Si no, navegamos a la vista normal
                Inicio(navController, zapatosVM, favoritoVM, compraViewModel)
            }
        }
        composable("Favorite"){
            Favorito(navController,favoritoVM,compraViewModel)
        }
        composable("Shop"){
            Tienda(navController)
        }
    }

}