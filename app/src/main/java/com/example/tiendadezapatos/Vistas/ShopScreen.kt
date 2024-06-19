package com.example.tiendadezapatos.Vistas

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tiendadezapatos.Componentes.MostrarDialogoConfirmacion
import com.example.tiendadezapatos.Componentes.TarjetaProducto
import com.example.tiendadezapatos.Componentes.TarjetaProductoComprados
import com.example.tiendadezapatos.R
import com.example.tiendadezapatos.ViewModels.CompraViewModel
import com.example.tiendadezapatos.ViewModels.ZapatosViewModel
import com.example.tiendadezapatos.banner.Banner
import com.example.tiendadezapatos.model.ZapatillaModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Tienda(navController: NavController, compraVM: CompraViewModel) {
    LaunchedEffect(key1 = true) {
        compraVM.obtenerZapatosComprados()
    }
    val datosZapatos by compraVM.zapatosComprado.observeAsState(listOf())
    val mostrarDialogo = remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            Banner(
                modifier = Modifier.fillMaxWidth()
                    .height(150.dp),
                fotoLogo = painterResource(R.drawable.logotienda),
                textoLugar = "Carrito",
                textoMarca = ""
            )
        },
        bottomBar = {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { navController.navigate("Inicio") }) {
                    Icon(painterResource(id = R.drawable.home), contentDescription = null, modifier = Modifier.size(40.dp))
                }
                IconButton(onClick = { navController.navigate("Shop") }) {
                    Icon(painterResource(id = R.drawable.compragris), contentDescription = null, modifier = Modifier.size(40.dp).background(color = Color(0,166,118,100)))
                }
                IconButton(onClick = { navController.navigate("Favorite") }) {
                    Icon(painterResource(id = R.drawable.favoritogris), contentDescription = null, modifier = Modifier.size(40.dp))
                }
                IconButton(onClick = { navController.navigate("Login") }) {
                    Icon(painterResource(id = R.drawable.login), contentDescription = null, modifier = Modifier.size(40.dp))
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {Button(
            onClick = { mostrarDialogo.value = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0,166,118,100))
        ) {
            Text("Terminar compra")
        }
            LazyColumn {
                items(datosZapatos) { zapato ->
                    TarjetaProductoComprados(zapato,compraVM,navController)
                }
            }
            MostrarDialogoConfirmacion(compraVM, mostrarDialogo)
        }
    }
}
