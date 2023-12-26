package com.test.suitmedia.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.suitmedia.activity.SecondActivity
import com.test.suitmedia.databinding.ItemListUserBinding
import com.test.suitmedia.response.DataItem

class ListUserAdapter : androidx.recyclerview.widget.ListAdapter<DataItem, ListUserAdapter.UserViewHolder>(DIFF_CALLBACK){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }


    class UserViewHolder(private val binding: ItemListUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: DataItem){
            binding.apply {
                Glide.with(root.context)
                    .load(user.avatar)
                    .into(imgItemPhoto)

                val fullName = "${user.firstName} ${user.lastName}"
                tvItemName.text = fullName
                tvItemEmail.text = user.email

                root.setOnClickListener {
                    val intent = Intent(root.context, SecondActivity::class.java)
                    intent.putExtra(EXTRA_USERNAME, fullName)
                    root.context.startActivity(intent)
                }
            }

        }
    }

    companion object {
        const val EXTRA_USERNAME = "USERNAME"
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}