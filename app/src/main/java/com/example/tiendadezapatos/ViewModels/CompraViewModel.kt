package com.example.tiendadezapatos.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tiendadezapatos.model.ZapatillaModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore

class CompraViewModel: ViewModel(){
    private val db = FirebaseFirestore.getInstance()
    private val auth: FirebaseAuth = Firebase.auth
    val zapatosComprado = MutableLiveData<List<ZapatillaModel>?>(listOf())

    fun comprarZapato(zapato: ZapatillaModel) {
        val email = auth.currentUser?.email
        if (zapato.stock.toInt() > 0) {
            val nuevoStock = zapato.stock.toInt() - 1
            db.collection("Zapatos").document(zapato.nombre).update("stock", nuevoStock)

            val docData = hashMapOf(
                "imagen" to zapato.imagen,
                "nombre" to zapato.nombre,
                "precio" to zapato.precio
            )
            db.collection("Usuarios").document(email.toString()).collection("compras").document(zapato.nombre).set(docData)

            val actualizar = zapato.copy(stock = nuevoStock.toString())
            val datos = zapatosComprado.value?.map { if (it.nombre == zapato.nombre) actualizar else it }
            zapatosComprado.value = datos
        } else {
            //futura funcion añadir a la lista de espera
        }
    }
}