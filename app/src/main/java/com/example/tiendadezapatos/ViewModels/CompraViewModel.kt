package com.example.tiendadezapatos.ViewModels

import android.content.ContentValues.TAG
import android.util.Log
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

    fun comprarZapato(nombreZapato: String) {
        val email = auth.currentUser?.email
        db.collection("Zapatos")
            .whereEqualTo("nombre", nombreZapato)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.documents.isNotEmpty()) {
                    val zapatoDocument = documents.documents[0]
                    val zapato = zapatoDocument.toObject(ZapatillaModel::class.java)
                    if (zapato != null && zapato.stock.toInt() > 0) {
                        val nuevoStock = zapato.stock.toInt() - 1
                        db.collection("Zapatos").document(zapatoDocument.id).update("stock", nuevoStock)

                        val docData = hashMapOf(
                            "imagen" to zapato.imagen,
                            "nombre" to zapato.nombre,
                            "precio" to zapato.precio
                        )
                        db.collection("Usuarios").document(email.toString()).collection("compras").document(zapatoDocument.id).set(docData)

                        val actualizar = zapato.copy(stock = nuevoStock.toString())
                        val datos = zapatosComprado.value?.map { if (it.nombre == zapato.nombre) actualizar else it }
                        zapatosComprado.value = datos
                    } else {
                        //futura funcion añadir a la lista de espera
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error obteniendo documentos: ", exception)
            }
    }

}