package com.autopartsx.autopartsapp.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.autopartsx.autopartsapp.model.Part

class PartViewModel : ViewModel() {

    val categories = listOf("Todos", "Motor", "Freios", "Suspensão", "Elétrica", "Transmissão", "Outros")

    private var nextPartId = 1

    val parts = mutableStateListOf<Part>()
    val partMessage = mutableStateOf<String?>(null)

    init {
        addPart("Filtro de Óleo", "Bosch", 39.90, "Filtro de óleo para motor 1.0/1.6")
        addPart("Pastilha de Freio", "TRW", 129.99, "Pastilha dianteira linha compacto")
    }

    fun addPart(
        name: String,
        brand: String,
        price: Double?,
        desc: String,
        category: String = "Outros"
    ): Boolean {
        if (name.isBlank() || brand.isBlank() || price == null) {
            partMessage.value = "Preencha nome, marca e preço."
            return false
        }
        parts.add(
            Part(
                id = nextPartId++,
                category = category,
                name = name,
                brand = brand,
                price = price,
                description = desc
            )
        )
        partMessage.value = "Peça adicionada."
        return true
    }


    fun updatePart(
        id: Int,
        name: String,
        brand: String,
        price: Double?,
        desc: String,
        category: String? = null
    ): Boolean {
        val idx = parts.indexOfFirst { it.id == id }
        if (idx < 0) {
            partMessage.value = "Peça não encontrada."
            return false
        }
        val p = parts[idx]
        if (name.isBlank() || brand.isBlank() || price == null) {
            partMessage.value = "Preencha nome, marca e preço."
            return false
        }
        p.name = name
        p.brand = brand
        p.price = price
        p.description = desc
        category?.let { p.category = it }   // << NOVO
        partMessage.value = "Peça atualizada."
        return true
    }


    fun deletePart(id: Int) {
        parts.removeAll { it.id == id }
        partMessage.value = "Peça removida."
    }

    fun getPartById(id: Int): Part? {
        return parts.find { it.id == id }
    }
}
