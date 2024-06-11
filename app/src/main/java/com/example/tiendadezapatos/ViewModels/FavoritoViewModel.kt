package com.example.tiendadezapatos.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tiendadezapatos.model.ZapatillaModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore

class FavoritoViewModel : ViewModel(){
    private val db = FirebaseFirestore.getInstance()
    private val auth: FirebaseAuth = Firebase.auth
    val zapatosFavoritos = MutableLiveData<List<ZapatillaModel>>(listOf())

    init {
        devolverZapatosFavoritos()
    }

    /**
     * Funcion para guardar una zapatilla en la coleccion favorito con el email del que inicio sesion
     * @param zapato da los detalles del zapato
     * @param favorito boleano que cuando es true guarda el zapato
     */
    fun Guardar(zapato: ZapatillaModel, favorito: Boolean) {
        val email = auth.currentUser?.email
        if (favorito) {
            // Guardar el zapato como favorito
            val docData = hashMapOf(
                "imagen" to zapato.imagen,
                "marca" to zapato.marca,
                "nombre" to zapato.nombre,
                "precio" to zapato.precio
            )
            db.collection("Usuarios").document(email.toString()).collection("favoritos").document(zapato.nombre).set(docData)

        } else {
            // Eliminar el zapato de los favoritos
            db.collection("Usuarios").document(email.toString()).collection("favoritos").document(zapato.nombre).delete()
            devolverZapatosFavoritos()

        }
    }

    /**
     * Devuelce todos los zapatos que este en la coleccion favortios
     */
    fun devolverZapatosFavoritos() {
        val email = auth.currentUser?.email
        db.collection("Usuarios").document(email.toString()).collection("favoritos").get().addOnSuccessListener { documents ->
            zapatosFavoritos.value = documents.mapNotNull { it.toObject(ZapatillaModel::class.java) }
        }
    }

    /**
     * Filtra los favoritos por las marcas
     * @param nombre de la marca
     */
    fun filtrarFavoritosPorMarca(marca: String) {
        val email = auth.currentUser?.email
        db.collection("Usuarios").document(email.toString()).collection("favoritos").get().addOnSuccessListener { documents ->
            zapatosFavoritos.value = documents.mapNotNull { it.toObject(ZapatillaModel::class.java) }.filter { it.marca == marca }
        }
    }
}