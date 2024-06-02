package com.example.nowweather

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlin.Unit

// WishlistAdapter.kt
class WishlistAdapter(
    private val wishlistItems: MutableList<WishlistItem>,
    private val onRemoveClick: (WishlistItem) -> Unit
) : RecyclerView.Adapter<WishlistAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.item_title)
        val imageView: ImageView = view.findViewById(R.id.item_image)
        val priceTextView: TextView = view.findViewById(R.id.item_price)
        val itemRemove: ImageView = view.findViewById(R.id.item_remove)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.wishlist_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = wishlistItems[position]
        holder.titleTextView.text = item.title
        holder.priceTextView.text = item.price

        Glide.with(holder.itemView.context)
            .load(item.image)
            .into(holder.imageView)

        holder.itemRemove.setOnClickListener {
            onRemoveClick(item)
        }

    }

    override fun getItemCount(): Int {
        return wishlistItems.size
    }
}

