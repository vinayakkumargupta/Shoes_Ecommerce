package com.univ.shoes.Orders

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.univ.shoes.ShopViewModel
import com.univ.shoes.databinding.ActivityOrdersBinding


class OrdersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrdersBinding
    private val shopViewModel: ShopViewModel by viewModels()

    private lateinit var upcomingAdapter: OrdersAdapter
    private lateinit var pastAdapter: OrdersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.backBtn.setOnClickListener {
            finish()
        }

        setupRecyclerViews()
        observeOrders()   // ✅ CORRECT PLACE
    }

    private fun setupRecyclerViews() {

        upcomingAdapter = OrdersAdapter { order ->
            openOrder(order.orderId)
        }

        pastAdapter = OrdersAdapter { order ->
            openOrder(order.orderId)
        }

        binding.upcomingRecycler.layoutManager =
            LinearLayoutManager(this)
        binding.upcomingRecycler.adapter = upcomingAdapter

        binding.pastRecycler.layoutManager =
            LinearLayoutManager(this)
        binding.pastRecycler.adapter = pastAdapter
    }

    private fun observeOrders() {

        shopViewModel.upcomingOrders.observe(this) { list ->

            upcomingAdapter.submitList(list)

            binding.upcomingEmpty.visibility =
                if (list.isEmpty()) View.VISIBLE else View.GONE
        }

        shopViewModel.pastOrders.observe(this) { list ->

            pastAdapter.submitList(list)

            binding.pastEmpty.visibility =
                if (list.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    private fun openOrder(orderId: String) {
        val intent = Intent(this, OrderSuccessActivity::class.java)
        intent.putExtra("order_id", orderId)
        intent.putExtra("from_orders", true)
        startActivity(intent)
    }
}



