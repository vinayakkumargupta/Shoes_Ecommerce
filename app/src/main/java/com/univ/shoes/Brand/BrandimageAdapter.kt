package com.univ.shoes.Brand

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.helper.widget.Carousel
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.univ.shoes.databinding.ItemBrandImagesBinding

class BrandimageAdapter(
    private val images: List<BrandImage>
) : RecyclerView.Adapter<BrandimageAdapter.VH>() {
    inner class VH(
        val binding: ItemBrandImagesBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BrandimageAdapter.VH {
        val binding = ItemBrandImagesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: BrandimageAdapter.VH, position: Int) {
        holder.binding.apply {
            Glide.with(holder.itemView.context)
                .load(images[position].imageResId)
                .into(imageView)
        }
    }

    override fun getItemCount(): Int {
        return images.count()

    }
}