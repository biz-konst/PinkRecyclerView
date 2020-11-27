package com.example.pinkrecycleradapter.core

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class PinkBaseAdapter<T>(val viewHolderProvider: PinkViewHolderProvider) :
    RecyclerView.Adapter<PinkViewHolder<T>>(),
    PinkViewHolderProvider.OnRepoChangeListener {

    init {
        @Suppress("LeakingThis")
        viewHolderProvider.registerOnChangeListener(this)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        viewHolderProvider.getViewHolder(parent, viewType) as PinkViewHolder<T>

    override fun onBindViewHolder(holder: PinkViewHolder<T>, position: Int) {
        holder.bind(holder, getItem(position), position)
    }

    override fun getItemViewType(position: Int) =
        viewHolderProvider.getItemType(getItem(position)!!::class)

    override fun onViewHolderRepoChange(provider: PinkViewHolderProvider) {
        notifyDataSetChanged()  // invalidate
    }

    abstract fun getItem(position: Int): T

}