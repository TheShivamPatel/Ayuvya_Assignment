package com.ayuvyaassignment.android.model

data class Product(
    val id : Int,
    val name: String,
    val price: Double,
    val mrp: Double,
    val rating: Float,
    val images: List<String>,
    val description: String,
    val isFeatured: Boolean = false
)