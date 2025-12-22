package com.univ.shoes.Whislist

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wishlist_table")
data class WishlistEntity(
    @PrimaryKey
    val productId: Int,

    val title: String,
    val price: Int,
    val imageResId: Int
)
