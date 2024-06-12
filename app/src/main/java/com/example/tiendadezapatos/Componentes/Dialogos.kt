package com.example.tiendadezapatos.Componentes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.tiendadezapatos.ViewModels.ZapatosViewModel
import com.example.tiendadezapatos.model.ZapatillaModel

@Composable
fun MostrarDialogoEditar(viewModel: ZapatosViewModel, nombreZapato: String, mostrarDialogo: MutableState<Boolean>) {
    // Usa 'remember' para guardar el estado de los campos de entrada
    val nombre = remember { mutableStateOf("") }
    val marca = remember { mutableStateOf("") }
    val precio = remember { mutableStateOf("") }
    val stock = remember { mutableStateOf("") }

    // Busca el zapato por su nombre y llena los TextField con los datos actuales del zapato
    LaunchedEffect(nombreZapato) {
        viewModel.db.collection("Zapatos")
            .whereEqualTo("nombre", nombreZapato)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.documents.isNotEmpty()) {
                    val zapato = documents.documents[0].toObject(ZapatillaModel::class.java)
                    if (zapato != null) {
                        nombre.value = zapato.nombre
                        marca.value = zapato.marca
                        precio.value = zapato.precio
                        stock.value = zapato.stock
                    }
                }
            }
    }

    if (mostrarDialogo.value) {
        Dialog(onDismissRequest = { mostrarDialogo.value = false }) {
            Card {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = "Editar Zapato")
                    TextField(value = nombre.value, onValueChange = { nombre.value = it }, label = { Text("Nombre") })
                    TextField(value = marca.value, onValueChange = { marca.value = it }, label = { Text("Marca") })
                    TextField(value = precio.value, onValueChange = { precio.value = it }, label = { Text("Precio") })
                    TextField(value = stock.value, onValueChange = { stock.value = it }, label = { Text("Stock") })
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(onClick = {
                            // Crea un nuevo objeto ZapatillaModel con los datos actualizados
                            val nuevoZapato = ZapatillaModel(
                                nombre = nombre.value,
                                marca = marca.value,
                                precio = precio.value,
                                stock = stock.value
                            )

                            // Actualiza el zapato en la base de datos
                            viewModel.actualizarZapato(nombreZapato, nuevoZapato)
                            viewModel.mostrarTodos()
                            mostrarDialogo.value = false
                        }) {
                            Text("Guardar")
                        }
                        Button(onClick = { mostrarDialogo.value = false
                            viewModel.mostrarTodos()}) {
                            Text("Cancelar")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MostrarDialogoCrear(viewModel: ZapatosViewModel, mostrarDialogo: MutableState<Boolean>) {
    // Usa 'remember' para guardar el estado de los campos de entrada
    val nombre = remember { mutableStateOf("") }
    val marca = remember { mutableStateOf("") }
    val precio = remember { mutableStateOf("") }
    val stock = remember { mutableStateOf("") }

    if (mostrarDialogo.value) {
        Dialog(onDismissRequest = { mostrarDialogo.value = false }) {
            Card {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = "Crear Zapato")
                    TextField(value = nombre.value, onValueChange = { nombre.value = it }, label = { Text("Nombre") })
                    TextField(value = marca.value, onValueChange = { marca.value = it }, label = { Text("Marca") })
                    TextField(value = precio.value, onValueChange = { precio.value = it }, label = { Text("Precio") })
                    TextField(value = stock.value, onValueChange = { stock.value = it }, label = { Text("Stock") })
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(onClick = {
                            // Crea un nuevo objeto ZapatillaModel con los datos ingresados
                            val nuevoZapato = ZapatillaModel(
                                nombre = nombre.value,
                                marca = marca.value,
                                precio = precio.value,
                                stock = stock.value
                            )

                            // Agrega el nuevo zapato a la base de datos
                            viewModel.agregarZapato(nuevoZapato)
                            viewModel.mostrarTodos()
                            mostrarDialogo.value = false
                        }) {
                            Text("Guardar")
                        }
                        Button(onClick = { mostrarDialogo.value = false
                            viewModel.mostrarTodos()}) {
                            Text("Cancelar")
                        }
                    }
                }
            }
        }
    }
}
