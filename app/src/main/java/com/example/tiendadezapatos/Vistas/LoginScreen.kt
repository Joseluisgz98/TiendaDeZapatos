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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tiendadezapatos.R
import com.example.tiendadezapatos.ViewModels.LoginViewModel
import com.example.tiendadezapatos.banner.Banner

@Composable
fun Login(loginVM:LoginViewModel,navController: NavController){
    Scaffold(
        topBar = {
            Banner(
                modifier = Modifier.fillMaxWidth()
                    .height(150.dp),
                fotoLogo = painterResource(R.drawable.logotienda),
                textoLugar = "Login"
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
            OutlinedTextField(
                value = loginVM.email,
                onValueChange = { loginVM.changeEmail(it) },
                label = { Text(text = "Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp)
            )

            OutlinedTextField(
                value = loginVM.password,
                onValueChange = { loginVM.changePassword(it) },
                label = { Text(text = "Password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp)
            )
            Button(
                onClick = {
                    loginVM.login { navController.navigate("Inicio") }
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0,166,118,100))
            ) {
                Text(text = "Entrar")
            }
            Text(
                text = buildAnnotatedString {
                    append("Regístrate ")
                    withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline, color = Color(0,166,118,100))) {
                        append(" Aquí")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp)
                    .clickable { navController.navigate("Register") },
                textAlign = TextAlign.Center
            )

        }
    }
}
