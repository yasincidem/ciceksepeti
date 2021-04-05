package com.yasincidem.ciceksepeti.ui.product

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.yasincidem.ciceksepeti.databinding.ProductListItemBinding
import com.yasincidem.ciceksepeti.datasource.model.Product

class ProductListAdapter :
    ListAdapter<Product, ProductListAdapter.ProductViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder =
        ProductViewHolder(
            ProductListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product)
    }

    class ProductViewHolder(
        private val binding: ProductListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.apply {
                productImage.apply {
                    load(product.largeImage) {
                        crossfade(true)
                    }
                }
                productName.text = product.name
                productOldPrice.apply {
                    visibility =
                        if (product.price?.old?.equals(0.0) == true) View.GONE else View.VISIBLE
                    text = "${product.price?.old} ${product.price?.currency}"
                    paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                }
                productCurrentPrice.text = "${product.price?.current} ${product.price?.currency}"
                productDiscount.apply {
                    visibility =
                        if (product.price?.discountPercentage == null) View.GONE else View.VISIBLE
                    text = "${product.price?.discountPercentage}%"
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem == newItem
        }
    }

}