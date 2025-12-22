package com.univ.shoes.Brand

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.univ.shoes.databinding.ItemBrandBinding

class BrandAdapter(
    private val brand : List<Brand>
) : RecyclerView.Adapter<BrandAdapter.BrandViewHolder>() {
    inner class BrandViewHolder(
        val binding: ItemBrandBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BrandViewHolder {
        val binding = ItemBrandBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BrandViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BrandViewHolder, position: Int) {
      holder.binding.apply {
          brandText.text = brand[position].brandName
          Glide.
          with(holder.itemView.context)
              .load(brand[position].brandImage)
              .into(brandimage)
      }
    }

    override fun getItemCount(): Int {
        return brand.size
    }
}