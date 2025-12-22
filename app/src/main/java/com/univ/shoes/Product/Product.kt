package com.univ.shoes.Product

import java.io.Serializable


data class Product(
    val cartId: Int = 0,      // 👈 ADD THIS
    val id: Int,
    val title: String,
    val desc: String = "",
    val size: Int = 0,
    val price: Int,
    val oldPrice: Int = 0,
    val rating: String = "",
    val images: List<Int> = emptyList(),
    var selectedImageIndex: Int = 0,
    val quantity: Int = 1,
    val selectedSize: String? = null
) : Serializable









