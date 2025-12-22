package com.univ.shoes.Whislist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.univ.shoes.Product.Product
import com.univ.shoes.R
import com.univ.shoes.databinding.ItemWishlistBinding

class WishlistAdapter(
    private val onClick: (Product) -> Unit
) : RecyclerView.Adapter<WishlistAdapter.WishlistVH>() {

    private val list = mutableListOf<Product>()

    fun submitList(newList: List<Product>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WishlistVH {
        val binding = ItemWishlistBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WishlistVH(binding)
    }

    override fun onBindViewHolder(holder: WishlistVH, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class WishlistVH(
        private val binding: ItemWishlistBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) = with(binding) {

            productImage.setImageResource(
                product.images[product.selectedImageIndex]
            )

            title.text = product.title
            price.text = "₹${product.price}"

//            root.setOnClickListener {
//                onClick(product)
//            }
        }
    }
}

