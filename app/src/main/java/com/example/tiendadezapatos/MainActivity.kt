package com.example.tiendadezapatos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.tiendadezapatos.Navegacion.NavManager
import com.example.tiendadezapatos.ViewModels.LoginViewModel
import com.example.tiendadezapatos.ViewModels.ZapatosViewModel
import com.example.tiendadezapatos.ui.theme.TiendaDeZapatosTheme

class MainActivity : ComponentActivity() {
    val loginVM : LoginViewModel by viewModels()
    val zapatosVM : ZapatosViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TiendaDeZapatosTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavManager(loginVM,zapatosVM)
                }
            }
        }
    }
}
