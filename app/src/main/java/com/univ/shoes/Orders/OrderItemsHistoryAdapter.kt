package com.univ.shoes.Orders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.univ.shoes.R

class OrderItemsHistoryAdapter(
    private val items: List<OrderItemEntity>
) : RecyclerView.Adapter<OrderItemsHistoryAdapter.ItemVH>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemVH {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order_history, parent, false)

        return ItemVH(view)
    }

    override fun onBindViewHolder(
        holder: ItemVH,
        position: Int
    ) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ItemVH(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private val productImage =
            itemView.findViewById<ImageView>(R.id.productImage)
        private val productTitle =
            itemView.findViewById<TextView>(R.id.productTitle)
        private val productQty =
            itemView.findViewById<TextView>(R.id.productQty)
        private val productPrice =
            itemView.findViewById<TextView>(R.id.productPrice)

        fun bind(item: OrderItemEntity) {

            productTitle.text = item.title
            productQty.text = "Qty: ${item.quantity}"
            productPrice.text = "₹${item.price * item.quantity}"

            productImage.setImageResource(item.imageRes)

        }
    }
}
