package com.example.pinkrecycleradapter.custom

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import com.example.pinkrecycleradapter.core.PinkViewHolder
import com.example.pinkrecycleradapter.core.PinkViewHolderDelegate

@Suppress("unused")
open class DataPinkViewHolderDelegate<T>(
    protected val binder: PinkViewHolderBinder<T>,
    protected val lifecycleOwner: LifecycleOwner? = null
) :
    PinkViewHolderDelegate<T> {

    override fun inflate(
        parent: ViewGroup,
        inflater: LayoutInflater,
        layoutId: Int
    ): ViewDataBinding =
        DataBindingUtil.inflate<ViewDataBinding>(inflater, layoutId, parent, false)
            .apply { lifecycleOwner = this@DataPinkViewHolderDelegate.lifecycleOwner }

    override fun bind(holder: PinkViewHolder<T>, item: T, position: Int) {
        binder.invoke(holder, item, position)
    }

}

typealias PinkViewHolderBinder<T> = (holder: PinkViewHolder<T>, item: T, position: Int) -> Unit