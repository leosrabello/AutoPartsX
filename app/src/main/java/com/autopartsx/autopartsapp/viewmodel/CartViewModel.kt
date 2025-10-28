package com.autopartsx.autopartsapp.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.autopartsx.autopartsapp.model.CartItem
import com.autopartsx.autopartsapp.model.Part

class CartViewModel : ViewModel() {

    val cartItems = mutableStateListOf<CartItem>()
    val cartMessage = mutableStateOf<String?>(null)

    fun addToCart(part: Part) {
        val existing = cartItems.find { it.part.id == part.id }
        if (existing != null) {
            existing.quantity += 1
        } else {
            cartItems.add(CartItem(part, 1))
        }
        cartMessage.value = "Adicionado ao carrinho."
    }

    fun removeFromCart(partId: Int) {
        cartItems.removeAll { it.part.id == partId }
        cartMessage.value = "Removido do carrinho."
    }

    fun changeQty(partId: Int, delta: Int) {
        val item = cartItems.find { it.part.id == partId }
        item?.let {
            it.quantity += delta
            if (it.quantity <= 0) {
                cartItems.remove(it)
            }
        }
    }

    fun totalPrice(): Double {
        return cartItems.sumOf { it.part.price * it.quantity }
    }

    fun checkout() {
        cartItems.clear()
        cartMessage.value = "Compra finalizada! (simulação)"
    }
}
