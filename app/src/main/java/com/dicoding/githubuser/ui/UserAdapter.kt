package com.dicoding.githubuser.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubuser.data.response.ItemsItem
import com.dicoding.githubuser.databinding.ItemListUserBinding

class UserAdapter : ListAdapter<ItemsItem, UserAdapter.MyViewHolder>(DIFF_CALLBACK) {

    var listener: RecyclerViewClickListener? = null

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val id= getItem(position)
        holder.bind(id)

        holder.itemView.setOnClickListener {
            listener?.onItemClick(it, getItem(position))
        }
    }
    class MyViewHolder(val binding: ItemListUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(id: ItemsItem){
            binding.rvTv.text = id.login
            Glide.with(binding.root.context)
                .load(id.avatarUrl)
                .into(binding.rvImage)
        }
    }

    interface RecyclerViewClickListener {
        fun onItemClick(view: View, item: ItemsItem)
    }
}


