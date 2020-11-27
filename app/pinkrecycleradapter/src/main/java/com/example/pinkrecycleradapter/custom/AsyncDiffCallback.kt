package com.example.pinkrecycleradapter.custom

import androidx.recyclerview.widget.DiffUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AsyncDiffCallback<T>(
    private val oldList: List<T>,
    private val newList: List<T>,
    private val diffItemCallback: DiffUtil.ItemCallback<T>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        if (oldItem == null) {
            return newItem == null
        } else if (newItem == null) {
            return false
        }
        return diffItemCallback.areItemsTheSame(oldItem, newItem)
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        if (oldItem == null) {
            return newItem == null
        } else if (newItem == null) {
            return false
        }
        return diffItemCallback.areContentsTheSame(oldItem, newItem)
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        if (oldItem == null || newItem == null) {
            return null
        }
        return diffItemCallback.getChangePayload(oldItem, newItem)
    }

    suspend fun asyncCalculateDiff(detectMoves: Boolean = true): DiffUtil.DiffResult =
        withContext(Dispatchers.Default) {
            DiffUtil.calculateDiff(this@AsyncDiffCallback, detectMoves)
        }

}