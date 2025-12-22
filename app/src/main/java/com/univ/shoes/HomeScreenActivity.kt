package com.univ.shoes

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.univ.shoes.Brand.Brand
import com.univ.shoes.Brand.BrandAdapter
import com.univ.shoes.Brand.BrandImage
import com.univ.shoes.Brand.BrandimageAdapter
import com.univ.shoes.Cart.CartActivity
import com.univ.shoes.Helper.BaseActivity
import com.univ.shoes.Helper.Helper
import com.univ.shoes.Product.Product
import com.univ.shoes.Product.ProductDetailActivity
import com.univ.shoes.Product.ProductImageAdapter
import com.univ.shoes.Whislist.WishlistActivity
import com.univ.shoes.databinding.ActivityMainBinding

class HomeScreenActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpUi()
    }

    fun setUpUi() {
        binding.apply {
            binding.home.setOnClickListener {
                openWithAnimation(HomeScreenActivity::class.java)
            }

            binding.cart.setOnClickListener {
                openWithAnimation(CartActivity::class.java)
            }

            binding.wishlist.setOnClickListener {
                openWithAnimation(WishlistActivity::class.java)
            }

            binding.profile.setOnClickListener {
                val intent = Intent(this@HomeScreenActivity, ProfileActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.stay)
            }


            val brands = listOf(
                Brand("Nike", R.drawable.nike),
                Brand("Adidas", R.drawable.adidas),
                Brand("Puma", R.drawable.puma),
                Brand("NB", R.drawable.nb),
                Brand("Reebok", R.drawable.reebook)
            )

            // Brands Upper
         brandlist.layoutManager = LinearLayoutManager(this@HomeScreenActivity,LinearLayoutManager.HORIZONTAL,false)
         brandlist.adapter = BrandAdapter(brands)
            val spacing = resources.getDimensionPixelSize(R.dimen.dp_12)
            brandlist.addItemDecoration(Helper.HorizontalSpaceItemDecoration(spacing))


            //Brand Crousel
            val images = listOf(
                BrandImage(R.drawable.banner1),
                BrandImage(R.drawable.banner2)
            )
            brandImageCarousel.adapter = BrandimageAdapter(images)
            dotsIndicator.setViewPager2(brandImageCarousel)


            //product list
            val products = listOf(

                Product(
                    id = 1,
                    title = "Nike Air Max 270",
                    desc = "Lightweight running shoes with responsive cushioning and breathable mesh upper for all-day comfort.",
                    size = 7,
                    price = 13000,
                    oldPrice = 19000,
                    rating = "4.9",
                    images = listOf(
                        R.drawable.shoes_3,
                        R.drawable.inside1,
                        R.drawable.inside2,
                        R.drawable.inside3,
                        R.drawable.inside4,
                        R.drawable.inside5
                    )
                ),

                Product(
                    id = 2,
                    title = "Adidas Ultraboost",
                    desc = "High-performance shoes featuring Boost midsole technology for superior energy return and comfort.",
                    size = 8,
                    price = 12999,
                    oldPrice = 17999,
                    rating = "4.8",
                    images = listOf(
                        R.drawable.shoes_2,
                        R.drawable.inside1,
                        R.drawable.inside2,
                        R.drawable.inside3,
                        R.drawable.inside4,
                        R.drawable.inside5
                    )
                ),

                Product(
                    id = 3,
                    title = "Puma RS-X",
                    desc = "Bold and stylish sneakers with cushioned sole and durable outsole for everyday casual wear.",
                    size = 9,
                    price = 8499,
                    oldPrice = 11999,
                    rating = "4.6",
                    images = listOf(
                        R.drawable.shoes_1,
                        R.drawable.inside1,
                        R.drawable.inside2,
                        R.drawable.inside3,
                        R.drawable.inside4,
                        R.drawable.inside5
                    )
                ),

                Product(
                    id = 4,
                    title = "Reebok Flexagon",
                    desc = "Versatile training shoes designed for flexibility, grip, and stability during workouts.",
                    size = 8,
                    price = 4999,
                    oldPrice = 6999,
                    rating = "4.5",
                    images = listOf(
                        R.drawable.shoes_4,
                        R.drawable.inside1,
                        R.drawable.inside2,
                        R.drawable.inside3,
                        R.drawable.inside4,
                        R.drawable.inside5
                    )
                )

            )
            allItem.layoutManager = GridLayoutManager(this@HomeScreenActivity,2)
            allItem.adapter = ProductImageAdapter(products)
            allItem.isNestedScrollingEnabled = false



        }

    }
    private fun openWithAnimation(activity: Class<*>) {
        val intent = Intent(this, activity)
        startActivity(intent)
        overridePendingTransition(
            R.anim.slide_in_right,
            R.anim.slide_out_left
        )
    }

    fun openProductDetail(product: Product) {
      val intent = Intent(this, ProductDetailActivity::class.java)
        intent.putExtra("product", product)
        startActivity(intent)

    }


}