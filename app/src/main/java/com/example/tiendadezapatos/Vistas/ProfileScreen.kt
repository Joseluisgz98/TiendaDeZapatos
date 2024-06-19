package com.example.tiendadezapatos.Vistas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tiendadezapatos.Componentes.MostrarDialogoPedidosRealizados
import com.example.tiendadezapatos.R
import com.example.tiendadezapatos.ViewModels.CompraViewModel
import com.example.tiendadezapatos.ViewModels.LoginViewModel
import com.example.tiendadezapatos.banner.Banner


@Composable
fun Perfil(navController: NavController,loginVM: LoginViewModel,compraVM: CompraViewModel){
    var email = loginVM.email
    val mostrarDialogo = remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            Banner(
                modifier = Modifier.fillMaxWidth()
                    .height(150.dp),
                fotoLogo = painterResource(R.drawable.logotienda),
                textoLugar = "Perfil"
            )
        },
        bottomBar = {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { navController.navigate("Inicio") }) {
                    Icon(painterResource(id = R.drawable.home), contentDescription = null,modifier = Modifier.size(40.dp))
                }
                IconButton(onClick = { navController.navigate("Shop") }) {
                    Icon(painterResource(id = R.drawable.compragris), contentDescription = null,modifier = Modifier.size(40.dp))
                }
                IconButton(onClick = { navController.navigate("Favorite") }) {
                    Icon(painterResource(id = R.drawable.favoritogris), contentDescription = null,modifier = Modifier.size(40.dp))
                }
                IconButton(onClick = { navController.navigate("Login") }) {
                    Icon(painterResource(id = R.drawable.login), contentDescription = null,modifier = Modifier.size(40.dp).background(color = Color(0,166,118,100)))
                }
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(25.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logotienda),
                    contentDescription = null,
                    modifier = Modifier.size(70.dp)
                )
                Spacer(modifier = Modifier.width(25.dp))
                Text(text = "New Life Shoe", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold))
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = email)
            }
            /**Text(
                text = buildAnnotatedString {
                    append("Resgitro de compras ")
                    withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline, color = Color(0,166,118,100))) {
                        append(" Aquí")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp)
                    .clickable {compraVM.obtenerPedidosRealizados()
                        mostrarDialogo.value = true },
                textAlign = TextAlign.Center
            )
**/
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    loginVM.cerrarSesion()
                    navController.navigate("Inicio")
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0,166,118,100))
            ) {
                Text(text = "CERRAR SESION")
            }
            if (mostrarDialogo.value) {
                MostrarDialogoPedidosRealizados(compraVM, mostrarDialogo)
            }
        }
    }
}
