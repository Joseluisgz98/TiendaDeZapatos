package com.example.tiendadezapatos.ViewModels

import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tiendadezapatos.model.ZapatillaModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.CountDownLatch

class CompraViewModel: ViewModel(){
    private val db = FirebaseFirestore.getInstance()
    private val auth: FirebaseAuth = Firebase.auth
    val zapatosComprado = MutableLiveData<List<ZapatillaModel>>(listOf())
    val email = auth.currentUser?.email
    val comprasRealizadas = MutableLiveData<List<Map<String, Any>>>(listOf())
    val pedidosRealizados = MutableLiveData<List<Map<String, Any>>>(listOf())

    fun comprarZapato(nombreZapato: String) {
        db.collection("Zapatos")
            .whereEqualTo("nombre", nombreZapato)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.documents.isNotEmpty()) {
                    val zapatoDocument = documents.documents[0]
                    val zapato = zapatoDocument.toObject(ZapatillaModel::class.java)
                    if (zapato != null && zapato.stock.toInt() > 0) {
                        val nuevoStock = (zapato.stock.toInt() - 1).toString()
                        db.collection("Zapatos").document(zapatoDocument.id).update("stock", nuevoStock)

                        val docData = hashMapOf(
                            "imagen" to zapato.imagen,
                            "nombre" to zapato.nombre,
                            "precio" to zapato.precio
                        )
                        db.collection("Usuarios").document(email.toString()).collection("compras").document().set(docData)

                        val actualizar = zapato.copy(stock = nuevoStock)
                        val datos = zapatosComprado.value?.map { if (it.nombre == zapato.nombre) actualizar else it }
                        zapatosComprado.value = datos!!
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error obteniendo documentos: ", exception)
            }
    }
    fun obtenerZapatosComprados() {
        db.collection("Usuarios").document(email.toString()).collection("compras")
            .get()
            .addOnSuccessListener { documents ->
                val zapatosComprados = documents.documents.mapNotNull { it.toObject(ZapatillaModel::class.java) }
                // Actualiza el valor de zapatosComprado con los zapatos comprados
                zapatosComprado.value = zapatosComprados
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error obteniendo documentos: ", exception)
            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun terminarCompra() {
        val zapatosComprados = zapatosComprado.value ?: listOf()
        val costoTotal = zapatosComprados.sumOf { it.precio.toInt() } // Asegúrate de que el precio es un número
        val horaActual = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))

        val pedidoData = hashMapOf(
            "zapatos" to zapatosComprados,
            "costoTotal" to costoTotal,
            "hora" to horaActual
        )

        db.collection("Usuarios").document(email.toString()).collection("pedidosRealizados").document().set(pedidoData)
            .addOnSuccessListener {
                Log.d(TAG, "Pedido realizado con éxito")
                // Limpia la lista de zapatos comprados
                zapatosComprado.value = listOf()

                // Borra todos los documentos en la colección "compras"
                db.collection("Usuarios").document(email.toString()).collection("compras")
                    .get()
                    .addOnSuccessListener { documents ->
                        for (document in documents) {
                            document.reference.delete()
                        }
                    }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error realizando pedido: ", exception)
            }
    }
    fun borrarZapato(nombreZapato: String) {
        db.collection("Usuarios").document(email.toString()).collection("compras")
            .whereEqualTo("nombre", nombreZapato)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.documents.isNotEmpty()) {
                    val zapatoDocument = documents.documents[0]
                    zapatoDocument.reference.delete()

                    // Incrementa el stock del zapato
                    db.collection("Zapatos")
                        .whereEqualTo("nombre", nombreZapato)
                        .get()
                        .addOnSuccessListener { zapatoDocuments ->
                            if (zapatoDocuments.documents.isNotEmpty()) {
                                val zapatoCompradoDocument = zapatoDocuments.documents[0]
                                val zapatoComprado = zapatoCompradoDocument.toObject(ZapatillaModel::class.java)
                                if (zapatoComprado != null) {
                                    val nuevoStock = (zapatoComprado.stock.toInt() + 1).toString()
                                    db.collection("Zapatos").document(zapatoCompradoDocument.id).update("stock", nuevoStock)
                                }
                            }
                        }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error borrando zapato: ", exception)
            }
    }
    fun obtenerPedidosRealizados() {
        db.collection("Usuarios").document(email.toString()).collection("pedidosRealizados")
            .get()
            .addOnSuccessListener { documents ->
                val pedidos = documents.documents.mapNotNull { document ->
                    val zapatosClaves = document.get("zapatos") as? List<String>
                    val costoTotal = document.get("costoTotal") as? Int
                    val hora = document.get("hora") as? String
                    if (zapatosClaves != null && costoTotal != null && hora != null) {
                        // Obtiene los detalles de cada zapato
                        val zapatos = zapatosClaves.mapNotNull { zapatoClave ->
                            var zapatoData: Map<String, Any>? = null
                            val latch = CountDownLatch(1)
                            db.collection("Zapatos").document(zapatoClave)
                                .get()
                                .addOnSuccessListener { zapatoDocument ->
                                    zapatoData = zapatoDocument.data as? Map<String, Any>
                                    latch.countDown()
                                }
                                .addOnFailureListener { exception ->
                                    Log.w(TAG, "Error obteniendo zapato: ", exception)
                                    latch.countDown()
                                }
                            latch.await()
                            zapatoData
                        }
                        mapOf("zapatos" to zapatos, "costoTotal" to costoTotal, "hora" to hora)
                    } else {
                        null
                    }
                }
                // Actualiza el valor de pedidosRealizados con los pedidos realizados
                pedidosRealizados.value = pedidos
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error obteniendo documentos: ", exception)
            }
    }





}