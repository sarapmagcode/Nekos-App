package com.example.nekosapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.nekosapp.R
import com.example.nekosapp.databinding.RandomImageItemBinding
import com.example.nekosapp.home.RandomImageStatus
import com.example.nekosapp.home.SpecificImageStatus
import com.example.nekosapp.network.Item

class RandomImageAdapter(
    private val itemList: List<Item>,
    private val status: RandomImageStatus?,
    private val onItemClicked: (Item) -> Unit
) :
    RecyclerView.Adapter<RandomImageAdapter.RandomImageViewHolder>() {

    inner class RandomImageViewHolder(private var binding: RandomImageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item) {
            item.imageUrl?.let {
                val imgUri = it.toUri().buildUpon().scheme("https").build()
                binding.randomImage.load(imgUri) {
                    placeholder(R.drawable.loading_animation)
                    error(R.drawable.ic_broken_image)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RandomImageViewHolder {

        /** This part makes the recyclerview match the full width (for view binding). */
        return RandomImageViewHolder(
            RandomImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: RandomImageViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.bind(currentItem)
        holder.itemView.setOnClickListener { onItemClicked(currentItem) }
    }
}
