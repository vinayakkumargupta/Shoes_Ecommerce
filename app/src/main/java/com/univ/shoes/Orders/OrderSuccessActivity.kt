package com.univ.shoes.Orders

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.univ.shoes.Helper.CartRepository
import com.univ.shoes.HomeScreenActivity
import com.univ.shoes.Product.Product
import com.univ.shoes.R
import com.univ.shoes.ShopViewModel
import com.univ.shoes.databinding.ActivityOrderSuccessBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class OrderSuccessActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderSuccessBinding
    private val shopViewModel: ShopViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOrderSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val orderId = intent.getStringExtra("order_id") ?: return
        val fromOrders = intent.getBooleanExtra("from_orders", false)

        binding.backBtn.visibility =
            if (fromOrders) View.VISIBLE else View.GONE

        observeOrderStatus(orderId)
        binding.backBtn.setOnClickListener { finish() }

        loadOrderFromDb()
        setupClicks()


    }

    // ----------------------------------
    // LOAD ORDER FROM DATABASE
    // ----------------------------------

    private fun observeOrderStatus(orderId: String) {

        shopViewModel.observeOrder(orderId).observe(this) { order ->

            when (order.status) {

                OrderStatus.SHIPPED -> {
                    updatePaymentStatus("PROCESSING")
                    updateTrackingUI(OrderStatus.SHIPPED)
                }

                OrderStatus.OUT_FOR_DELIVERY -> {
                    updatePaymentStatus("PROCESSING")
                    updateTrackingUI(OrderStatus.OUT_FOR_DELIVERY)
                }

                OrderStatus.DELIVERED -> {
                    updatePaymentStatus("COMPLETED")
                    updateTrackingUI(OrderStatus.DELIVERED)
                }
            }
        }
    }
    private fun updateTrackingUI(status: String) {
        when (status) {

            OrderStatus.SHIPPED -> {
                binding.dotShipped.setBackgroundResource(R.drawable.dot_active)
                binding.dotOFD.setBackgroundResource(R.drawable.dot_inactive)
                binding.dotDelivered.setBackgroundResource(R.drawable.dot_inactive)
            }

            OrderStatus.OUT_FOR_DELIVERY -> {
                binding.dotShipped.setBackgroundResource(R.drawable.dot_active)
                binding.dotOFD.setBackgroundResource(R.drawable.dot_active)
                binding.dotDelivered.setBackgroundResource(R.drawable.dot_inactive)
            }

            OrderStatus.DELIVERED -> {
                binding.dotShipped.setBackgroundResource(R.drawable.dot_active)
                binding.dotOFD.setBackgroundResource(R.drawable.dot_active)
                binding.dotDelivered.setBackgroundResource(R.drawable.dot_active)
            }
        }
    }


    private fun loadOrderFromDb() {

        val orderId = intent.getStringExtra("order_id") ?: return

        lifecycleScope.launch {

            // 1️⃣ Load order items
            val items = shopViewModel.getOrderItems(orderId)

            if (items.isEmpty()) return@launch

            // 2️⃣ Order ID
            binding.orderId.text = orderId

            // 3️⃣ Amount calculation
            val totalAmount = items.sumOf { it.price * it.quantity }
            binding.amountPaid.text = "₹$totalAmount"

            // 4️⃣ RecyclerView
            binding.orderItemsRecycler.layoutManager =
                LinearLayoutManager(this@OrderSuccessActivity)

            binding.orderItemsRecycler.adapter =
                OrderItemsHistoryAdapter(items)

            // 5️⃣ Status (for now UPCOMING)
            updatePaymentStatus("UPCOMING")
            updateTrackingUI("UPCOMING")
            val images = items.map { it.imageRes }

            binding.orderPager.adapter =
                OrderImagePagerAdapter(images)

        }
    }

    // ----------------------------------
    // PAYMENT STATUS UI
    // ----------------------------------
    private fun updatePaymentStatus(status: String) {
        binding.paymentStatus.text =
                "Payment Status: PAID"
    }


    // ----------------------------------
    // BUTTON ACTION
    // ----------------------------------
    private fun setupClicks() {
        binding.continueShopping.setOnClickListener {
            val intent = Intent(this, HomeScreenActivity::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }
}


