package com.example.tiendadezapatos.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tiendadezapatos.model.ZapatillaModel
import com.google.firebase.firestore.FirebaseFirestore


class ZapatosViewModel: ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val shoesRef = db.collection("Zapatos")
    val shoesData = MutableLiveData<List<ZapatillaModel>>(listOf())

    init {
        getShoesData()
    }
    private fun getShoesData() {
        shoesRef.get().addOnSuccessListener { documents ->
            shoesData.value = documents.mapNotNull { it.toObject(ZapatillaModel::class.java) }
        }
    }
}