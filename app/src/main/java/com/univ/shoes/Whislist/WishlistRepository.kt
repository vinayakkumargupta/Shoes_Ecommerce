package com.univ.shoes.Whislist

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.univ.shoes.Product.Product

class WishlistRepository(context: Context) {

    private val dao = CartDatabase.getDatabase(context).wishlistDao()

    fun getWishlist(): LiveData<List<Product>> =
        dao.getWishlist().map { list ->
            list.map {
                Product(
                    id = it.productId,
                    title = it.title,
                    desc = "",                // ✅ REQUIRED
                    size = 0,
                    price = it.price,
                    oldPrice = it.price,
                    rating = "0",
                    images = listOf(it.imageResId),
                    selectedImageIndex = 0,
                    quantity = 1,
                    selectedSize = null
                )
            }
        }


    suspend fun toggle(product: Product) {
        if (dao.isWishlisted(product.id)) {
            dao.delete(product.id)
        } else {
            dao.insert(
                WishlistEntity(
                    productId = product.id,
                    title = product.title,
                    price = product.price,
                    imageResId = product.images[product.selectedImageIndex]
                )
            )
        }
    }

    suspend fun isWishlisted(productId: Int): Boolean {
        return dao.isWishlisted(productId)
    }
}
