package com.univ.shoes

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.univ.shoes.Orders.OrderSuccessActivity
import com.univ.shoes.Orders.OrdersActivity
import com.univ.shoes.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.closeBtn.setOnClickListener {
            closeProfile()
        }

        // Click listeners (optional)
        binding.accountItem.setOnClickListener { }
        binding.ordersItem.setOnClickListener {
            val intent = Intent(this, OrdersActivity::class.java)
            startActivity(intent)
        }
        binding.helpItem.setOnClickListener { }
        binding.tcItem.setOnClickListener { }
        binding.logoutBtn.setOnClickListener { }
    }

    private fun closeProfile() {
        finish()
        overridePendingTransition(0, R.anim.slide_out_right)
    }
}

