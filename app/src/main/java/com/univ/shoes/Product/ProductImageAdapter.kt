package com.univ.shoes.Product

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.univ.shoes.HomeScreenActivity
import com.univ.shoes.databinding.ItemProductBinding

class ProductImageAdapter(
    private val products: List<Product>
) : RecyclerView.Adapter<ProductImageAdapter.ProductVH>() {

    init {
        setHasStableIds(true)   // ✅ IMPORTANT
    }

    override fun getItemId(position: Int): Long {
        return products[position].id.toLong()   // ✅ UNIQUE ID
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductVH {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductVH(binding)
    }

    override fun onBindViewHolder(holder: ProductVH, position: Int) {
        holder.setIsRecyclable(false)

        val item = products[position]

        holder.binding.apply {
            title.text = item.title
            price.text = "₹${item.price}"
            ratingNum.text = item.rating

            // ✅ Correct way to clear recycled image
            productImage.setImageResource(item.images[0])

        }

        holder.itemView.setOnClickListener {
            (holder.itemView.context as HomeScreenActivity)
                .openProductDetail(item)
        }
    }

    override fun getItemCount() = products.size

    class ProductVH(val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root)
}


