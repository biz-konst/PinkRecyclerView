package com.example.pinkrecycleradapter.core

import android.view.ViewGroup

interface PinkViewHolderProvider {

    val count: Int

    fun getItemType(item: Any): Int

    fun getViewHolder(parent: ViewGroup, viewType: Int): PinkViewHolder<*>

    fun registerOnChangeListener(listenerRepo: OnRepoChangeListener)

    fun unregisterOnChangeListener(listenerRepo: OnRepoChangeListener)

    interface OnRepoChangeListener {

        fun onViewHolderRepoChange(provider: PinkViewHolderProvider)

    }

}
