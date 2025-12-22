package com.univ.shoes.Whislist

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.univ.shoes.Product.ProductDetailActivity
import com.univ.shoes.R
import com.univ.shoes.ShopViewModel

class WishlistActivity : AppCompatActivity() {

    private lateinit var adapter: WishlistAdapter
    private val shopViewModel: ShopViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wishlist)

        adapter = WishlistAdapter { product ->
            val intent = Intent(this, ProductDetailActivity::class.java)
            intent.putExtra("product", product)
            startActivity(intent)
        }
        findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            finish()
        }


        val recycler = findViewById<RecyclerView>(R.id.wishlistRecycler)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        shopViewModel.wishlistItems.observe(this) {
            adapter.submitList(it)
        }
    }
}
