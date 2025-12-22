package com.univ.shoes.Product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.univ.shoes.R

class ImageSelectorAdapter(
    private val images: List<Int>,
    private val onClick: (Int) -> Unit
) : RecyclerView.Adapter<ImageSelectorAdapter.VH>() {

    private var selectedIndex = 0

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.colorImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_color, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.image.setImageResource(images[position])
        holder.image.alpha = if (position == selectedIndex) 1f else 0.4f

        holder.image.setOnClickListener {
            val old = selectedIndex
            selectedIndex = position

            notifyItemChanged(old)
            notifyItemChanged(selectedIndex)

            onClick(position)
        }
    }

    override fun getItemCount() = images.size
}


