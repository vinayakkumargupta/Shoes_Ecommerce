package com.univ.shoes.Product

import android.app.Fragment
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.univ.shoes.Cart.CartActivity
import com.univ.shoes.Helper.BaseActivity
import com.univ.shoes.R
import com.univ.shoes.ShopViewModel
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope

import com.univ.shoes.databinding.ActivityProductDetailBinding
import kotlinx.coroutines.launch

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailBinding
    private lateinit var product: Product

    private var quantity = 1
    private var selectedSize: String? = null

    private val shopViewModel: ShopViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        product = intent.getSerializableExtra("product") as Product

        setupUi()
        setupImageSelector()
        setupClicks()
    }


    /* ---------------- UI SETUP ---------------- */

    private fun setupUi() {
        binding.apply {

            // Main image (default)
            productImage.setImageResource(
                product.images[product.selectedImageIndex]
            )

            title.text = product.title
            desc.text = product.desc
            rating.text = product.rating

            price.text = "₹${product.price}"
            oldPrice.text = "₹${product.oldPrice}"
            oldPrice.paintFlags =
                oldPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

            quantityTv.text = quantity.toString()
            finalPrice.text = "₹${product.price * quantity}"

            updateWishlistIcon()
        }
    }

    /* ---------------- IMAGE SELECTOR ---------------- */

    private fun setupImageSelector() {
        binding.colorRecycler.apply {
            layoutManager = LinearLayoutManager(
                this@ProductDetailActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = ImageSelectorAdapter(product.images) { index ->
                product.selectedImageIndex = index
                binding.productImage.setImageResource(product.images[index])
            }
        }
    }




    /* ---------------- CLICKS ---------------- */

    private fun setupClicks() {
        binding.apply {

            // Back
            btnBack.setOnClickListener { finish() }

            // Quantity +
            plusBtn.setOnClickListener {
                quantity++
                updateQuantityUi()
            }

            // Quantity -
            minusBtn.setOnClickListener {
                if (quantity > 1) {
                    quantity--
                    updateQuantityUi()
                }
            }

            // Size selection
            sizeChipGroup.setOnCheckedChangeListener { group, checkedId ->
                if (checkedId != View.NO_ID) {
                    val chip = group.findViewById<Chip>(checkedId)
                    selectedSize = chip.text.toString()
                }
            }

            btnRight.setOnClickListener {
                lifecycleScope.launch {
                    shopViewModel.toggleWishlist(product)
                    updateWishlistIcon()
                }
            }


            buyNowBtn.setOnClickListener {

                if (selectedSize == null) {
                    Toast.makeText(
                        this@ProductDetailActivity,
                        "Please select size",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                val cartProduct = product.copy(
                    selectedImageIndex = product.selectedImageIndex,
                    quantity = quantity,
                    selectedSize = selectedSize
                )

                shopViewModel.addToCart(cartProduct)

                openCartScreen()
            }

        }
    }
    private fun openCartScreen() {
        val intent = Intent(this, CartActivity::class.java)
        intent.putExtra("open_cart", true)
        startActivity(intent)
    }


    /* ---------------- HELPERS ---------------- */

    private fun updateQuantityUi() {
        binding.quantityTv.text = quantity.toString()
        binding.finalPrice.text = "₹${product.price * quantity}"
    }

    private fun updateWishlistIcon() {
        lifecycleScope.launch {
            val isWishlisted = shopViewModel.isWishlisted(product.id)
            binding.btnRight.setImageResource(
                if (isWishlisted) R.drawable.ic_fill_heart
                else R.drawable.btn_3
            )
        }
    }

}





