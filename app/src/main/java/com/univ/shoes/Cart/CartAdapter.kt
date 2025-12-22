package com.univ.shoes.Cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.univ.shoes.Product.Product
import com.univ.shoes.databinding.ItemCartBinding

class CartAdapter(
    private val onDeleteClick: (Product) -> Unit
) : ListAdapter<Product, CartAdapter.CartVH>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartVH {
        val binding = ItemCartBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CartVH(binding)
    }

    override fun onBindViewHolder(holder: CartVH, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CartVH(
        private val binding: ItemCartBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.apply {
                productImage.setImageResource(
                    product.images[product.selectedImageIndex]
                )

                title.text = product.title
                size.text = "Size: ${product.selectedSize}"
                quantity.text = "Qty: ${product.quantity}"
                price.text = "₹${product.price * product.quantity}"

                deleteBtn.setOnClickListener {
                    onDeleteClick(product)
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product) =
            oldItem.id == newItem.id &&
                    oldItem.selectedSize == newItem.selectedSize

        override fun areContentsTheSame(oldItem: Product, newItem: Product) =
            oldItem == newItem
    }
}

