package com.univ.shoes.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CartDao {

    @Query("SELECT * FROM cart_table")
    fun getCartItems(): LiveData<List<CartEntity>>

    @Insert
    suspend fun insert(item: CartEntity)

    // ✅ CORRECT DELETE
    @Query("DELETE FROM cart_table WHERE cartId = :cartId")
    suspend fun deleteByCartId(cartId: Int)

    @Query("DELETE FROM cart_table")
    suspend fun clearCart()
}

