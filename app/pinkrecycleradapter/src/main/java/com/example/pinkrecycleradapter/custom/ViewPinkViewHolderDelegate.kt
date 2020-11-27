package com.example.pinkrecycleradapter.custom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pinkrecycleradapter.core.PinkViewHolderDelegate

@Suppress("unused")
open class ViewPinkViewHolderDelegate<T> : PinkViewHolderDelegate<T> {

    override fun inflate(parent: ViewGroup, inflater: LayoutInflater, layoutId: Int): View =
        inflater.inflate(layoutId, parent, false)

}