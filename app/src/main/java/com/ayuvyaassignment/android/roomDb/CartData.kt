package com.ayuvyaassignment.android.roomDb
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ayuvya_cart_table")
data class CartData(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val productId: Int,
    var quantity: Int,
    val name: String,
    val price: Double,
    val mrp: Double,
    val image: String,
)