package com.example.tiendadezapatos.Componentes

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.tiendadezapatos.R
import com.example.tiendadezapatos.ViewModels.CompraViewModel
import com.example.tiendadezapatos.ViewModels.FavoritoViewModel
import com.example.tiendadezapatos.ViewModels.ZapatosViewModel
import com.example.tiendadezapatos.Vistas.ImageFromUrl
import com.example.tiendadezapatos.model.ZapatillaModel
import com.google.firebase.firestore.DocumentReference

/**
 * Funcion la cual crea cada tarjeta de los zapatos para el admin
 * @param zapato pasa el objeto zapato el cual tienes los valores que tendra la tarjeta
 */
@Composable
fun TarjetaProducto(zapato: ZapatillaModel,zapatosVM: ZapatosViewModel) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .size(300.dp)
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
            }
            Text(text = zapato.nombre, style = MaterialTheme.typography.titleLarge) // Asumiendo que 'nombre' contiene "Adidas Gazelle"
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { /* acción de editar aquí */ }) {
                    Image(painter = painterResource(id = R.drawable.editar), contentDescription = "Editar",modifier = Modifier.size(48.dp))
                }

                IconButton(onClick = { zapatosVM.eliminarZapatoPorNombre(zapato.nombre)
                    zapatosVM.mostrarTodos()}) {
                    Image(painter = painterResource(id = R.drawable.borrar), contentDescription = "Editar",modifier = Modifier.size(48.dp))
                }
            }
        }
    }
}
/**
 * Funcion la cual crea cada tarjeta de los zapatos
 * @param zapato pasa el objeto zapato el cual tienes los valores que tendra la tarjeta
 */
@Composable
fun TarjetaCarta(zapato: ZapatillaModel, favoritoVM: FavoritoViewModel, compraViewModel: CompraViewModel) {
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
            Button(onClick = { compraViewModel.comprarZapato(zapato.nombre) },modifier = Modifier
                .fillMaxWidth(),colors = ButtonDefaults.buttonColors(containerColor = Color(0,166,118,100))) {
                Text(text = "Comprar")
            }
        }
    }
}



