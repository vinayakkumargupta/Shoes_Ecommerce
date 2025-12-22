package com.univ.shoes.Orders

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import com.univ.shoes.Database.AppDatabase
import com.univ.shoes.Product.Product
import com.univ.shoes.Whislist.CartDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OrderRepository(context: Context) {

    private val dao =
        AppDatabase.getDatabase(context).orderDao()

    fun getUpcomingOrders(): LiveData<List<OrderEntity>> =
        dao.getUpcomingOrders()

    fun getPastOrders(): LiveData<List<OrderEntity>> =
        dao.getPastOrders()

    suspend fun placeOrder(
        orderId: String,
        amount: Int,
        products: List<Product>
    ) {
        dao.insertOrder(
            OrderEntity(
                orderId = orderId,
                amount = amount,
                status = "UPCOMING",
                createdAt = System.currentTimeMillis()
            )
        )
        scheduleOrderProgress(orderId)

        dao.insertOrderItems(
            products.map {
                OrderItemEntity(
                    orderId = orderId,
                    productId = it.id,
                    title = it.title,
                    price = it.price,
                    quantity = it.quantity,
                    imageRes = it.images[it.selectedImageIndex]
                )
            }
        )

        // ✅ Schedule delivery
        scheduleOrderProgress(orderId)
    }

    fun observeOrder(orderId: String): LiveData<OrderEntity> =
        dao.observeOrder(orderId)


    fun scheduleOrderProgress(orderId: String) {

        CoroutineScope(Dispatchers.IO).launch {

            // STEP 1: Shipped (immediate)
            dao.updateStatus(orderId, OrderStatus.SHIPPED)

            delay(60_000) // 1 minute

            // STEP 2: Out for delivery
            dao.updateStatus(orderId, OrderStatus.OUT_FOR_DELIVERY)

            delay(60_000) // next 1 minute

            // STEP 3: Delivered
            dao.updateStatus(orderId, OrderStatus.DELIVERED)
        }
    }



    suspend fun getOrderItems(orderId: String): List<OrderItemEntity> =
        dao.getOrderItems(orderId)
}

