package com.example.pinkrecycleradapter.custom

import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pinkrecycleradapter.core.PinkBaseAdapter
import com.example.pinkrecycleradapter.core.PinkViewHolderProvider
import java.util.*
import kotlin.properties.Delegates

@Suppress("unused", "MemberVisibilityCanBePrivate")
open class PinkGroupNodeAdapter<T>(
    provider: PinkViewHolderProvider,
    override val subItemsAdapter: PinkBaseAdapter<*>,
    data: T,
    typeId: Any? = null
) : PinkBaseAdapter<Any>(provider), PinkGroupNode<T> {

    val collapsedState = ObservableBoolean(true)

    private val typeId = typeId ?: data!!::class

    override var data: T = data
        set(value) {
            field = value
            notifyItemChanged(0)
        }

    override var collapsed: Boolean
        get() = collapsedState.get()
        set(value) = setCollapse(value)

    override fun getItemViewType(position: Int) =
        if (position == 0) viewHolderProvider.getItemType(typeId)
        else subItemsAdapter.getItemViewType(position - 1)

    override fun getItemId(position: Int) =
        if (position == 0) GROUP_ITEM_ID else subItemsAdapter.getItemId(position - 1)

    override fun getItem(position: Int) =
        if (position == 0) this else subItemsAdapter.getItem(position - 1) as Any

    override fun getItemCount() = if (collapsed) 1 else subItemsAdapter.itemCount + 1

    protected fun setCollapse(value: Boolean) {
        if (collapsedState.get() == value) return

        collapsedState.set(value)
        notifyCollapseChanged()
    }

    protected fun notifyCollapseChanged() {
        notifyItemChanged(0)
        if (collapsed)
            notifyItemRangeRemoved(1, subItemsAdapter.itemCount)
        else
            notifyItemRangeInserted(1, subItemsAdapter.itemCount)
    }

    companion object {

        const val GROUP_ITEM_ID = -1L

    }

}