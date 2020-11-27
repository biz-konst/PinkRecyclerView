package com.example.pinkrecycleradapter.custom

import com.example.pinkrecycleradapter.core.BasePinkViewHolderProvider

@Suppress("unused")
fun BasePinkViewHolderProvider.from(
    vararg layoutIds: Int,
    delegate: ViewPinkViewHolderDelegate<*> = ViewPinkViewHolderDelegate<Nothing>()
) {
    for (i in layoutIds.indices) {
        add(i, layoutIds[i], delegate)
    }
}