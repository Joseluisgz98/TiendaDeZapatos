package com.example.tiendadezapatos.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tiendadezapatos.model.ZapatillaModel
import com.google.firebase.firestore.FirebaseFirestore


class ZapatosViewModel: ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val shoesRef = db.collection("Zapatos")
    val datosZapatos = MutableLiveData<List<ZapatillaModel>>(listOf())




    init {
        mostrarTodos()
    }
    fun mostrarTodos() {
        shoesRef.get().addOnSuccessListener { documents ->
            datosZapatos.value = documents.mapNotNull { it.toObject(ZapatillaModel::class.java) }
        }
    }

    /**
     * Funcion para poder filtrar por marcas
     */
    fun filtrarPorMarca(marca: String) {
        shoesRef.get().addOnSuccessListener { documents ->
            datosZapatos.value = documents.mapNotNull { it.toObject(ZapatillaModel::class.java) }.filter { it.marca == marca }
        }
    }


}