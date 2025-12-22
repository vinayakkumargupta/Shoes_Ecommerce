package com.univ.shoes.Whislist

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WishlistDao {

    @Query("SELECT * FROM wishlist_table")
    fun getWishlist(): LiveData<List<WishlistEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: WishlistEntity)

    @Query("DELETE FROM wishlist_table WHERE productId = :productId")
    suspend fun delete(productId: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM wishlist_table WHERE productId = :productId)")
    suspend fun isWishlisted(productId: Int): Boolean
}
