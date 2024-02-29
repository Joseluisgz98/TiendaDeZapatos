package com.example.tiendadezapatos.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tiendadezapatos.model.ZapatillaModel
import com.google.firebase.firestore.FirebaseFirestore


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



}