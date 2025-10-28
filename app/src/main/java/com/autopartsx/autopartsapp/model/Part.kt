package com.autopartsx.autopartsapp.model

data class Part(
    val id: Int,
    var name: String,
    var brand: String,
    var price: Double,
    var description: String
)
