package com.example.tiendadezapatos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tiendadezapatos.ViewModels.LoginViewModel
import com.example.tiendadezapatos.Vistas.Inicio
import com.example.tiendadezapatos.Vistas.Register
import com.example.tiendadezapatos.ui.theme.TiendaDeZapatosTheme

class MainActivity : ComponentActivity() {
    val loginVM : LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TiendaDeZapatosTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Register(loginVM)
                }
            }
        }
    }
}
