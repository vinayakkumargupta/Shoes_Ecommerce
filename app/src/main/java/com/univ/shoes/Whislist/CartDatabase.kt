package com.univ.shoes.Whislist

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.univ.shoes.Database.CartDao
import com.univ.shoes.Database.CartEntity
import com.univ.shoes.Orders.OrderDao
import com.univ.shoes.Orders.OrderEntity
import com.univ.shoes.Orders.OrderItemEntity

@Database(
    entities = [
        CartEntity::class,
        WishlistEntity::class,
        OrderEntity::class,
        OrderItemEntity::class // ✅ ADD THIS (VERY IMPORTANT)
    ],
    version = 5,              // ✅ INCREMENT VERSION
    exportSchema = false
)
abstract class CartDatabase : RoomDatabase() {

    abstract fun cartDao(): CartDao
    abstract fun wishlistDao(): WishlistDao
    abstract fun orderDao(): OrderDao   // ✅ NOW VALID

    companion object {
        @Volatile
        private var INSTANCE: CartDatabase? = null

        fun getDatabase(context: Context): CartDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    CartDatabase::class.java,
                    "cart_db"
                )
                    .fallbackToDestructiveMigration() // OK for dev
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}
