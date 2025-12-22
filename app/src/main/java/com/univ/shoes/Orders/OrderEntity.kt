package com.univ.shoes.Orders

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey
    val orderId: String,

    val amount: Int,
    val status: String, // UPCOMING / DELIVERED
    val createdAt: Long
)
