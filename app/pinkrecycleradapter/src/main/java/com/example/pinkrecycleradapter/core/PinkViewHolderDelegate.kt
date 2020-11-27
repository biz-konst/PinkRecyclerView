package com.example.pinkrecycleradapter.core

import android.view.LayoutInflater
import android.view.ViewGroup

interface PinkViewHolderDelegate<T> {

    fun inflate(parent: ViewGroup, inflater: LayoutInflater, layoutId: Int): Any

    fun bind(holder: PinkViewHolder<T>, item: T, position: Int) {}

}
