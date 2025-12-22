package com.univ.shoes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.univ.shoes.Helper.CartRepository
import com.univ.shoes.Orders.OrderEntity
import com.univ.shoes.Orders.OrderItemEntity
import com.univ.shoes.Orders.OrderRepository
import com.univ.shoes.Product.Product
import com.univ.shoes.Whislist.WishlistRepository
import kotlinx.coroutines.launch


class ShopViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CartRepository(application)
    private val wishlistRepo = WishlistRepository(application)

    // CART
    val cartItems: LiveData<List<Product>> = repository.getCartItems()



    // WISHLIST (in-memory)
    private val _wishlistItems = MutableLiveData<List<Product>>(emptyList())

    private val orderRepo = OrderRepository(application)

    val upcomingOrders: LiveData<List<OrderEntity>> =
        orderRepo.getUpcomingOrders()

    val pastOrders: LiveData<List<OrderEntity>> =
        orderRepo.getPastOrders()

    val wishlistItems: LiveData<List<Product>> =
            wishlistRepo.getWishlist()


        fun addToCart(product: Product) {
            viewModelScope.launch {
                repository.insertCartItem(product)
            }
        }

    fun observeOrder(orderId: String): LiveData<OrderEntity> =
        orderRepo.observeOrder(orderId)



    fun removeFromCart(product: Product) {
            viewModelScope.launch {
                repository.deleteCartItem(product)
            }
        }

        fun clearCart() {
            viewModelScope.launch {
                repository.clearCart()
            }
        }

        fun toggleWishlist(product: Product) {
            viewModelScope.launch {
                wishlistRepo.toggle(product)
            }
        }



    fun placeOrder(amount: Int, cartList: List<Product>): String {

        val orderId = "ORD-${System.currentTimeMillis()}"

        viewModelScope.launch {
            orderRepo.placeOrder(orderId, amount, cartList)
        }

        return orderId
    }

    suspend fun getOrderItems(orderId: String) =
        orderRepo.getOrderItems(orderId)



    suspend fun isWishlisted(productId: Int): Boolean {
            return wishlistRepo.isWishlisted(productId)
        }
    }


