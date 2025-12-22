package com.univ.shoes.Database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_table")
data class CartEntity(
    @PrimaryKey(autoGenerate = true)
    val cartId: Int = 0,

    val productId: Int,
    val title: String,
    val price: Int,
    val quantity: Int,
    val selectedSize: String?,
    val imageResId: Int   // ✅ ADD THIS
)
