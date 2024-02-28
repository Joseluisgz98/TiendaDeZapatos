package com.example.tiendadezapatos.Vistas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tiendadezapatos.R
import com.example.tiendadezapatos.ViewModels.ZapatosViewModel
import com.example.tiendadezapatos.banner.Banner
import com.example.tiendadezapatos.model.ZapatillaModel

@Composable
fun Inicio(navController: NavController,zapatosVM: ZapatosViewModel){
    val shoesData by zapatosVM.shoesData.observeAsState(listOf())
    Scaffold(
        topBar = {
            Banner(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                fotoLogo = painterResource(R.drawable.logotienda),
                textoLugar = "Inicio",
                textoMarca = "Marcas"
            )
        },
        bottomBar = {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { navController.navigate("Inicio") }) {
                    Icon(painterResource(id = R.drawable.home), contentDescription = null,modifier = Modifier
                        .size(40.dp)
                        .background(color = Color(0, 166, 118, 100)))
                }
                IconButton(onClick = { navController.navigate("Shop") }) {
                    Icon(painterResource(id = R.drawable.compragris), contentDescription = null,modifier = Modifier.size(40.dp))
                }
                IconButton(onClick = { navController.navigate("Favorite") }) {
                    Icon(painterResource(id = R.drawable.favoritogris), contentDescription = null,modifier = Modifier.size(40.dp))
                }
                IconButton(onClick = { navController.navigate("Login") }) {
                    Icon(painterResource(id = R.drawable.login), contentDescription = null,modifier = Modifier.size(40.dp))
                }
            }
        },

        ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            LazyRow {
                item {
                    Card(modifier = Modifier
                        .padding(8.dp)
                        .size(71.dp, 29.dp)
                        .clickable { }) {
                        Image(
                            painter = painterResource(id = R.drawable.imagenadidas),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
                item {
                    Card(modifier = Modifier
                        .padding(8.dp)
                        .size(71.dp, 29.dp)
                        .clickable { }) {
                        Image(
                            painter = painterResource(id = R.drawable.imagenpuma),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
                item {
                    Card(modifier = Modifier
                        .padding(8.dp)
                        .size(71.dp, 29.dp)
                        .clickable { }) {
                        Image(
                            painter = painterResource(id = R.drawable.imagenvans),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
                item {
                    Card(modifier = Modifier
                        .padding(8.dp)
                        .size(71.dp, 29.dp)
                        .clickable { }) {
                        Image(
                            painter = painterResource(id = R.drawable.imagennike),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
                item {
                    Card(modifier = Modifier
                        .padding(8.dp)
                        .size(71.dp, 29.dp)
                        .clickable { }) {
                        Image(
                            painter = painterResource(id = R.drawable.imagendc),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
                item {
                    Card(modifier = Modifier
                        .padding(8.dp)
                        .size(71.dp, 29.dp)
                        .clickable { }) {
                        Image(
                            painter = painterResource(id = R.drawable.imagencovers),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
            LazyColumn {
                items(shoesData) { shoe ->
                    Card {
                        Column {
                            Text(text = "Marca: ${shoe.marca}")
                            Text(text = "Nombre: ${shoe.nombre}")
                            Text(text = "Precio: ${shoe.precio}")
                        }
                    }
                }


            }
        }
    }
}


