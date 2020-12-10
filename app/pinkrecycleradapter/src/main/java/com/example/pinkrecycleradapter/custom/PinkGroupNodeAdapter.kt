package com.example.pinkrecycleradapter.custom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pinkrecycleradapter.core.PinkBaseAdapter
import com.example.pinkrecycleradapter.core.PinkViewHolderProvider
import java.util.*
import kotlin.properties.Delegates

@Suppress("unused")
open class PinkGroupNodeAdapter<T>(
    provider: PinkViewHolderProvider,
    override val subItemsAdapter: PinkBaseAdapter<*>,
    data: T,
    private val typeId: Any
) : PinkBaseAdapter<Any>(provider), PinkGroupNode<T> {

    private val _collapsedState = MutableLiveData<Boolean>().apply { value = true }
    val collapsedState: LiveData<Boolean> = _collapsedState

    override var data: T = data
        set(value) {
            field = value
            notifyItemChanged(0)
        }

    override var collapsed: Boolean
        get() = _collapsedState.value ?: true
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
        if (_collapsedState.value != value) {
            _collapsedState.value = value
            notifyCollapseChanged()
        }
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