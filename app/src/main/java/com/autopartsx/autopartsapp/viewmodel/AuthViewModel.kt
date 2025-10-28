package com.autopartsx.autopartsapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.autopartsx.autopartsapp.model.User

class AuthViewModel : ViewModel() {

    private val users = mutableListOf<User>()
    private var nextUserId = 1

    var currentUser = mutableStateOf<User?>(null)
        private set

    var authMessage = mutableStateOf<String?>(null)
        private set

    fun register(name: String, email: String, password: String): Boolean {
        if (name.isBlank() || email.isBlank() || password.isBlank()) {
            authMessage.value = "Preencha todos os campos."
            return false
        }
        if (users.any { it.email.equals(email, ignoreCase = true) }) {
            authMessage.value = "Email já cadastrado."
            return false
        }

        val newUser = User(
            id = nextUserId++,
            name = name.trim(),
            email = email.trim(),
            password = password
        )
        users.add(newUser)
        currentUser.value = newUser
        authMessage.value = "Cadastro realizado."
        return true
    }

    fun login(email: String, password: String): Boolean {
        val found = users.find { it.email.equals(email.trim(), true) && it.password == password }
        return if (found != null) {
            currentUser.value = found
            authMessage.value = "Login OK."
            true
        } else {
            authMessage.value = "Credenciais inválidas."
            false
        }
    }

    fun logout() {
        currentUser.value = null
        authMessage.value = null
    }
}
