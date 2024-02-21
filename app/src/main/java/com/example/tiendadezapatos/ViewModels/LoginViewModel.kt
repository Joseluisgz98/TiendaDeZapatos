package com.example.tiendadezapatos.ViewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tiendadezapatos.model.UsuarioModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception


class LoginViewModel:ViewModel(){

    private val auth:FirebaseAuth = Firebase.auth
    private val firestore = Firebase.firestore

    var showAlert by mutableStateOf(false)
        private set
    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var userName by mutableStateOf("")
        private set

    fun login(onSuccess: () -> Unit){
        viewModelScope.launch {
            try {
                // DCS - Utiliza el servicio de autenticación de Firebase para validar al usuario
                // por email y contraseña
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            onSuccess()
                        } else {
                            Log.d("ERROR EN FIREBASE","Usuario y/o contrasena incorrectos")
                            showAlert = true
                        }
                    }
            } catch (e: Exception){
                Log.d("ERROR EN JETPACK", "ERROR: ${e.localizedMessage}")
            }
        }
    }
    fun createUser(onSuccess: () -> Unit){
        viewModelScope.launch {
            try {
                // DCS - Utiliza el servicio de autenticación de Firebase para registrar al usuario
                // por email y contraseña
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // DCS - Si se realiza con éxito, almacenamos el usuario en la colección "Users"
                            saveUser(userName)
                            onSuccess()
                        } else {
                            Log.d("ERROR EN FIREBASE","Error al crear usuario")
                            showAlert = true
                        }
                    }
            } catch (e: Exception){
                Log.d("ERROR CREAR USUARIO", "ERROR: ${e.localizedMessage}")
            }
        }
    }
    private fun saveUser(username: String){
        val id = auth.currentUser?.uid
        val email = auth.currentUser?.email

        viewModelScope.launch(Dispatchers.IO) {
            val usuario = UsuarioModel(
                userId = id.toString(),
                email = email.toString(),
                username = username
            )
            // DCS - Añade el usuario a la colección "Users" en la base de datos Firestore
            firestore.collection("Usuarios")
                .add(usuario)
                .addOnSuccessListener { Log.d("GUARDAR OK", "Se guardó el usuario correctamente en Firestore") }
                .addOnFailureListener { Log.d("ERROR AL GUARDAR", "ERROR al guardar en Firestore") }
        }
    }
    /**
     * Actualiza el email del usuario.
     *
     * @param email Nuevo email a establecer.
     */
    fun changeEmail(email: String) {
        this.email = email
    }

    /**
     * Actualiza la contraseña del usuario.
     *
     * @param password Nueva contraseña a establecer.
     */
    fun changePassword(password: String) {
        this.password = password
    }

    /**
     * Actualiza el nombre de usuario.
     *
     * @param userName Nuevo nombre de usuario a establecer.
     */
    fun changeUserName(userName: String) {
        this.userName = userName
    }

}