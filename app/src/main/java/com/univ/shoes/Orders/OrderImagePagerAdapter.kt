package com.univ.shoes.Orders

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.univ.shoes.Product.Product

class OrderImagePagerAdapter(
    private val images: List<Int>
) : RecyclerView.Adapter<OrderImagePagerAdapter.VH>() {

    inner class VH(val image: ImageView) :
        RecyclerView.ViewHolder(image)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {

        val paddingDp = 20
        val paddingPx =
            (paddingDp * parent.context.resources.displayMetrics.density).toInt()

        val iv = ImageView(parent.context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            scaleType = ImageView.ScaleType.FIT_XY
            setPadding(paddingPx, paddingPx, paddingPx, paddingPx)
        }
        return VH(iv)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.image.setImageResource(images[position])
    }

    override fun getItemCount() = images.size
}

