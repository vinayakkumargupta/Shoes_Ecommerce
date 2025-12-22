package com.univ.shoes.Orders

import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.univ.shoes.Product.Product

class OrderItemsAdapter(
    private val items: List<Product>
) : RecyclerView.Adapter<OrderItemsAdapter.VH>() {

    inner class VH(val tv: TextView) :
        RecyclerView.ViewHolder(tv)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val tv = TextView(parent.context).apply {
            setPadding(16, 12, 16, 12)
            textSize = 16f
        }
        return VH(tv)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val p = items[position]
        holder.tv.text =
            "• ${p.title} (Size: ${p.selectedSize}, Qty: ${p.quantity})"
    }

    override fun getItemCount() = items.size
}
