package com.example.pinkrecycleradapter.custom

import com.example.pinkrecycleradapter.core.BasePinkViewHolderProvider
import com.example.pinkrecycleradapter.core.PinkBaseAdapter

@Suppress("unused")
open class PinkEnumAdapter(viewHolderProvider: BasePinkViewHolderProvider) :
    PinkBaseAdapter<Int>(viewHolderProvider) {

    override fun getItem(position: Int) = position

    override fun getItemCount() = viewHolderProvider.count

}