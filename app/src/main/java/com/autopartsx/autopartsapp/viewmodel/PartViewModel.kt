package com.autopartsx.autopartsapp.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.autopartsx.autopartsapp.model.Part

class PartViewModel : ViewModel() {

    private var nextPartId = 1

    val parts = mutableStateListOf<Part>()
    val partMessage = mutableStateOf<String?>(null)

    init {
        addPart("Filtro de Óleo", "Bosch", 39.90, "Filtro de óleo para motor 1.0/1.6")
        addPart("Pastilha de Freio", "TRW", 129.99, "Pastilha dianteira linha compacto")
    }

    fun addPart(name: String, brand: String, price: Double?, desc: String): Boolean {
        if (name.isBlank() || brand.isBlank() || price == null || desc.isBlank()) {
            partMessage.value = "Preencha tudo."
            return false
        }

        val newPart = Part(
            id = nextPartId++,
            name = name.trim(),
            brand = brand.trim(),
            price = price,
            description = desc.trim()
        )
        parts.add(newPart)
        partMessage.value = "Peça adicionada."
        return true
    }

    fun updatePart(id: Int, name: String, brand: String, price: Double?, desc: String): Boolean {
        val p = parts.find { it.id == id }
        if (p != null && price != null && name.isNotBlank() && brand.isNotBlank() && desc.isNotBlank()) {
            p.name = name.trim()
            p.brand = brand.trim()
            p.price = price
            p.description = desc.trim()
            partMessage.value = "Peça atualizada."
            return true
        }
        partMessage.value = "Erro ao atualizar."
        return false
    }

    fun deletePart(id: Int) {
        parts.removeAll { it.id == id }
        partMessage.value = "Peça removida."
    }

    fun getPartById(id: Int): Part? {
        return parts.find { it.id == id }
    }
}
