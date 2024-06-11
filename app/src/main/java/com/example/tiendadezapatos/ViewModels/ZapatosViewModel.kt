package com.example.tiendadezapatos.ViewModels

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tiendadezapatos.model.ZapatillaModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore


class ZapatosViewModel: ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val zapatoRef = db.collection("Zapatos")//Referencia para acceder a la coleccion zapatos
    val datosZapatos = MutableLiveData<List<ZapatillaModel>>(listOf())



    init {
        mostrarTodos()
    }

    /**
     * muestra todos los zapatos
     */
    fun mostrarTodos() {
        zapatoRef.get().addOnSuccessListener { documents ->
            datosZapatos.value = documents.mapNotNull { it.toObject(ZapatillaModel::class.java) }
        }
    }

    /**
     * Funcion para poder filtrar por marcas
     * @param marca nombre de la marca
     */
    fun filtrarPorMarca(marca: String) {
        zapatoRef.get().addOnSuccessListener { documents ->
            datosZapatos.value = documents.mapNotNull { it.toObject(ZapatillaModel::class.java) }.filter { it.marca == marca }
        }
    }
    fun eliminarZapatoPorNombre(nombreZapato: String) {
        zapatoRef
            .whereEqualTo("nombre", nombreZapato)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.documents.isNotEmpty()) {
                    val zapatoId = documents.documents[0].id
                    zapatoRef
                        .document(zapatoId)
                        .delete()
                        .addOnSuccessListener { Log.d(TAG, "el zapato fue borrado con el ID: $zapatoId") }
                        .addOnFailureListener { e -> Log.w(TAG, "Error", e) }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error obteniendo documentos: ", exception)
            }
    }



}