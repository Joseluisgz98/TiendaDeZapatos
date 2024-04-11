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
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import coil.compose.rememberImagePainter
import com.example.tiendadezapatos.ViewModels.CompraViewModel
import com.example.tiendadezapatos.ViewModels.FavoritoViewModel
import com.example.tiendadezapatos.model.ZapatillaModel

@Composable
fun Inicio(navController: NavController,zapatosVM: ZapatosViewModel,favoritoVM: FavoritoViewModel,compraViewModel: CompraViewModel){
    val datosZapatos by zapatosVM.datosZapatos.observeAsState(listOf())
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
                IconButton(onClick = { navController.navigate("Favorite")
                favoritoVM.zapatosFavoritos}) {
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
                        .clickable { zapatosVM.filtrarPorMarca("Adidas") }) {
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
                        .clickable { zapatosVM.filtrarPorMarca("Puma") }) {
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
                        .clickable { zapatosVM.filtrarPorMarca("Vans") }) {
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
                        .clickable { zapatosVM.filtrarPorMarca("Nike") }) {
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
                        .clickable { zapatosVM.filtrarPorMarca("Dc") }) {
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
                        .clickable { zapatosVM.filtrarPorMarca("Converse") }) {
                        Image(
                            painter = painterResource(id = R.drawable.imagencovers),
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
                        .clickable { zapatosVM.mostrarTodos() }) {
                        Image(
                            painter = painterResource(id = R.drawable.all),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

            }
            LazyVerticalGrid(GridCells.Fixed(2),) {
                items(datosZapatos) { zapato ->
                    TarjetaCarta(zapato,favoritoVM,compraViewModel)
                }
            }
        }
    }
}

/**
 * Funcion para pasar una url para transformarlo en una imagen
 * @param url es la url que coge de firebase
 */
@Composable
fun ImageFromUrl(url: String) {
    val image = rememberImagePainter(data = url)
    Image(
        painter = image,
        contentDescription = null,
        modifier = Modifier.size(110.dp)
    )
}

/**
 * Funcion la cual crea cada tarjeta de los zapatos
 * @param zapato pasa el objeto zapato el cual tienes los valores que tendra la tarjeta
 */
@Composable
fun TarjetaCarta(zapato: ZapatillaModel,favoritoVM: FavoritoViewModel,compraViewModel: CompraViewModel) {
    val favorito = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ImageFromUrl(url = zapato.imagen)
                IconButton(onClick = {
                    favorito.value = !favorito.value
                    favoritoVM.Guardar(zapato, favorito.value)
                }) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "Favorito",
                        tint = if (favorito.value) Color(0,166,118) else Color.White
                    )
                }
            }
            Text(text = buildAnnotatedString {
                append(zapato.nombre)
                withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline, color = Color(0,166,118))) {
                    append(" ${zapato.precio} €")
                }
            }, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { compraViewModel.comprarZapato(zapato) },modifier = Modifier
                .fillMaxWidth(),colors = ButtonDefaults.buttonColors(containerColor = Color(0,166,118,100))) {
                Text(text = "Comprar")
            }
        }
    }
}



