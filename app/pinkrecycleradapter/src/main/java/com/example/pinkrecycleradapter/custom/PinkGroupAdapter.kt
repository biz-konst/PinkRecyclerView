package com.example.pinkrecycleradapter.custom

import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pinkrecycleradapter.core.PinkBaseAdapter
import com.example.pinkrecycleradapter.core.PinkViewHolder
import com.example.pinkrecycleradapter.core.PinkViewHolderProvider

@Suppress("unused")
open class PinkGroupAdapter(
    viewHolderProvider: PinkViewHolderProvider,
    config: ConcatAdapter.Config? = null
) :
    PinkBaseAdapter<PinkGroupNode<*>>(viewHolderProvider) {

    protected val adapterDelegate = config?.let { ConcatAdapter(it) } ?: ConcatAdapter()

    private val onItemTouchListener = object : RecyclerView.SimpleOnItemTouchListener() {
        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
            if (e.action == MotionEvent.ACTION_DOWN || e.action == MotionEvent.ACTION_CANCEL)
                rv.itemAnimator?.endAnimations()
            return super.onInterceptTouchEvent(rv, e)
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PinkViewHolder<PinkGroupNode<*>> =
        adapterDelegate.createViewHolder(parent, viewType) as PinkViewHolder<PinkGroupNode<*>>

    override fun onBindViewHolder(holder: PinkViewHolder<PinkGroupNode<*>>, position: Int) {
        adapterDelegate.onBindViewHolder(holder, position)
    }

    override fun getItemCount() = adapterDelegate.itemCount

    override fun getItemViewType(position: Int) = adapterDelegate.getItemViewType(position)

    override fun findRelativeAdapterPositionIn(
        adapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>,
        viewHolder: RecyclerView.ViewHolder,
        localPosition: Int
    ) = adapterDelegate.findRelativeAdapterPositionIn(adapter, viewHolder, localPosition)

    override fun setHasStableIds(hasStableIds: Boolean) {
        adapterDelegate.setHasStableIds(hasStableIds)
    }

    override fun onViewRecycled(holder: PinkViewHolder<PinkGroupNode<*>>) {
        adapterDelegate.onViewRecycled(holder)
    }

    override fun onFailedToRecycleView(holder: PinkViewHolder<PinkGroupNode<*>>) =
        adapterDelegate.onFailedToRecycleView(holder)

    override fun onViewAttachedToWindow(holder: PinkViewHolder<PinkGroupNode<*>>) {
        adapterDelegate.onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: PinkViewHolder<PinkGroupNode<*>>) {
        adapterDelegate.onViewDetachedFromWindow(holder)
    }

    override fun registerAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
        adapterDelegate.registerAdapterDataObserver(observer)
    }

    override fun unregisterAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
        adapterDelegate.unregisterAdapterDataObserver(observer)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        recyclerView.addOnItemTouchListener(onItemTouchListener)
        adapterDelegate.onAttachedToRecyclerView(recyclerView)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        recyclerView.removeOnItemTouchListener(onItemTouchListener)
        adapterDelegate.onDetachedFromRecyclerView(recyclerView)
    }

    override fun setStateRestorationPolicy(strategy: StateRestorationPolicy) {
        adapterDelegate.stateRestorationPolicy = strategy
    }

    override fun getItem(position: Int): PinkGroupNode<*> =
        adapterDelegate.adapters[position] as PinkGroupNodeAdapter<*>

    fun <T> addNode(
        data: T,
        typeId: Any,
        subItemsAdapter: PinkBaseAdapter<*>
    ): PinkGroupAdapter =
        apply {
            adapterDelegate.addAdapter(
                PinkGroupNodeAdapter(viewHolderProvider, subItemsAdapter, data, typeId)
            )
        }

    fun <T> addNode(
        position: Int,
        data: T,
        typeId: Any,
        subItemsAdapter: PinkBaseAdapter<*>
    ): PinkGroupAdapter =
        apply {
            adapterDelegate.addAdapter(
                position,
                PinkGroupNodeAdapter(viewHolderProvider, subItemsAdapter, data, typeId)
            )
        }

    fun remove(position: Int) {
        adapterDelegate.removeAdapter(getItem(position) as PinkGroupNodeAdapter)
    }

}