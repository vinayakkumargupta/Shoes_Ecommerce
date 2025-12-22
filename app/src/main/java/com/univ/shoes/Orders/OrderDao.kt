package com.univ.shoes.Orders

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface OrderDao {

    // 🔵 UPCOMING = everything NOT delivered
    @Query("""
        SELECT * FROM orders
        WHERE status != :delivered
        ORDER BY createdAt DESC
    """)
    fun getUpcomingOrders(
        delivered: String = OrderStatus.DELIVERED
    ): LiveData<List<OrderEntity>>

    // 🟢 PAST = delivered only
    @Query("""
        SELECT * FROM orders
        WHERE status = :delivered
        ORDER BY createdAt DESC
    """)
    fun getPastOrders(
        delivered: String = OrderStatus.DELIVERED
    ): LiveData<List<OrderEntity>>

    @Insert
    suspend fun insertOrder(order: OrderEntity)

    @Insert
    suspend fun insertOrderItems(items: List<OrderItemEntity>)

    @Query("SELECT * FROM order_items WHERE orderId = :orderId")
    suspend fun getOrderItems(orderId: String): List<OrderItemEntity>

    @Query("SELECT * FROM orders WHERE orderId = :orderId")
    fun observeOrder(orderId: String): LiveData<OrderEntity>

    @Query("UPDATE orders SET status = :status WHERE orderId = :orderId")
    suspend fun updateStatus(orderId: String, status: String)
}


