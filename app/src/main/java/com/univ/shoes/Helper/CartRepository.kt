package com.univ.shoes.Helper

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.univ.shoes.Database.CartEntity
import com.univ.shoes.Product.Product
import com.univ.shoes.Whislist.CartDatabase

class CartRepository(context: Context) {

    private val cartDao = CartDatabase.getDatabase(context).cartDao()

    // =========================
    // GET CART ITEMS
    // =========================
    fun getCartItems(): LiveData<List<Product>> {
        return cartDao.getCartItems().map { entities ->
            entities.map {
                Product(
                    cartId = it.cartId,          // 👈 VERY IMPORTANT
                    id = it.productId,
                    title = it.title,
                    price = it.price,
                    quantity = it.quantity,
                    selectedSize = it.selectedSize,
                    images = listOf(it.imageResId),
                    selectedImageIndex = 0
                )
            }
        }
    }


    // =========================
    // INSERT ITEM
    // =========================
    suspend fun insertCartItem(product: Product) {
        cartDao.insert(
            CartEntity(
                productId = product.id,
                title = product.title,
                price = product.price,
                quantity = product.quantity,
                selectedSize = product.selectedSize,
                imageResId = product.images
                    .getOrNull(product.selectedImageIndex)
                    ?: product.images.first() // ✅ SAFE FALLBACK
            )
        )
    }

    // =========================
    // DELETE ITEM (IMPORTANT FIX)
    // =========================
    suspend fun deleteCartItem(product: Product) {
        cartDao.deleteByCartId(product.cartId)
    }


    // =========================
    // CLEAR CART
    // =========================
    suspend fun clearCart() {
        cartDao.clearCart()
    }
}

