package com.example.pinkrecycleradapter.core

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class PinkViewHolder<T>(
    itemView: View,
    val binding: ViewDataBinding?,
    private val delegate: PinkViewHolderDelegate<T>
) : RecyclerView.ViewHolder(itemView) {

    val itemView: View
        get() = super.itemView

    fun bind(holder: PinkViewHolder<T>, item: T, position: Int) {
        delegate.bind(holder = holder, item = item, position = position)
        binding?.executePendingBindings()
    }

}

