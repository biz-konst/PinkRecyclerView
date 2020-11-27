package com.example.pinkrecycleradapter.custom

import com.example.pinkrecycleradapter.core.PinkBaseAdapter

interface PinkGroupNode<T> {

    var data: T
    var collapsed: Boolean
    val subItemsAdapter: PinkBaseAdapter<*>

}