package com.univ.shoes.Orders

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.univ.shoes.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class OrdersAdapter(
    private val onClick: (OrderEntity) -> Unit
) : ListAdapter<OrderEntity, OrdersAdapter.OrderVH>(Diff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderVH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order, parent, false)
        return OrderVH(view)
    }

    override fun onBindViewHolder(holder: OrderVH, position: Int) {
        holder.bind(getItem(position))
    }

    inner class OrderVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val orderId = itemView.findViewById<TextView>(R.id.orderIdText)
        private val amount = itemView.findViewById<TextView>(R.id.amountText)
        private val status = itemView.findViewById<TextView>(R.id.statusText)

        fun bind(order: OrderEntity) {

            orderId.text = order.orderId
            amount.text = "₹${order.amount}"

            if (order.status == "DELIVERED") {
                status.text = "Delivered"
                status.setTextColor(Color.parseColor("#2E7D32"))
            } else {
                status.text = "On the way"
                status.setTextColor(Color.parseColor("#EF6C00"))
            }

            itemView.setOnClickListener {
                onClick(order)
            }
        }
    }

    class Diff : DiffUtil.ItemCallback<OrderEntity>() {
        override fun areItemsTheSame(old: OrderEntity, new: OrderEntity) =
            old.orderId == new.orderId

        override fun areContentsTheSame(old: OrderEntity, new: OrderEntity) =
            old == new
    }
}

