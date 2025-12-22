package com.univ.shoes.Cart

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.univ.shoes.HomeScreenActivity
import com.univ.shoes.Orders.OrderSuccessActivity
import com.univ.shoes.Product.Product
import com.univ.shoes.ShopViewModel
import com.univ.shoes.databinding.ActivityCartBinding
import kotlinx.coroutines.launch

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private val shopViewModel: ShopViewModel by viewModels()
    private lateinit var cartAdapter: CartAdapter

    private var isCheckoutInProgress = false


    private val DELIVERY_FEE = 50
    private val GST_PERCENT = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecycler()
        observeCart()
        setupClicks()
    }

    private fun setupRecycler() {
        cartAdapter = CartAdapter { product ->
            // ✅ FIX: pass full product
            shopViewModel.removeFromCart(product)
        }

        binding.cartRecycler.layoutManager = LinearLayoutManager(this)
        binding.cartRecycler.adapter = cartAdapter
    }


    private fun updatePrice(cartList: List<Product>) {
        val subTotal = cartList.sumOf {
            it.price * it.quantity
        }

        val gst = (subTotal * GST_PERCENT) / 100
        val finalAmount = subTotal + gst + DELIVERY_FEE

        binding.subTotal.text = "₹$subTotal"
        binding.deliveryFee.text = "₹$DELIVERY_FEE"
        binding.gst.text = "₹$gst"
        binding.totalPrice.text = "₹$finalAmount"
        binding.checkoutBtn.text = "CHECKOUT ₹$finalAmount"
    }
    private fun observeCart() {
        shopViewModel.cartItems.observe(this) { cartList ->

            if (cartList.isEmpty()) {
                showEmptyState()
            } else {
                showCartState(cartList)
            }
        }
    }
    private fun showEmptyState() {
        binding.emptyCartLayout.visibility = View.VISIBLE
        binding.cartRecycler.visibility = View.GONE
        binding.bottomBar.visibility = View.GONE
    }

    private fun showCartState(cartList: List<Product>) {
        binding.emptyCartLayout.visibility = View.GONE
        binding.cartRecycler.visibility = View.VISIBLE
        binding.bottomBar.visibility = View.VISIBLE

        cartAdapter.submitList(cartList)
        updatePrice(cartList)
    }



    private fun goToHome() {
        val intent = Intent(this, HomeScreenActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }


    private fun setupClicks() {
        binding.btnBack.setOnClickListener { finish() }
        binding.goShoppingBtn.setOnClickListener {
            finish() // go back to previous screen (Home)
        }


        binding.checkoutBtn.setOnClickListener {

            val cartList = shopViewModel.cartItems.value ?: return@setOnClickListener
            if (cartList.isEmpty()) return@setOnClickListener

            val amount = binding.totalPrice.text
                .toString().replace("₹", "").toInt()

            val orderId = shopViewModel.placeOrder(amount, cartList)

            showSuccessAnimation(cartList, amount, orderId)
        }
    }


    private fun showSuccessAnimation(
        cartList: List<Product>,
        amount: Int,
        orderId: String
    ) {
        // 1️⃣ Show success UI
        binding.successOverlay.visibility = View.VISIBLE
        binding.successLottie.playAnimation()

        // 2️⃣ Clear cart ONCE order is saved
        shopViewModel.clearCart()

        // 3️⃣ Navigate after animation
        Handler(Looper.getMainLooper()).postDelayed({

            val intent = Intent(this, OrderSuccessActivity::class.java)
            intent.putExtra("order_id", orderId)
            startActivity(intent)
            finish()

        }, 2000)
    }




}

