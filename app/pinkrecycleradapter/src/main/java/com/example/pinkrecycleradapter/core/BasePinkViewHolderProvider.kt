package com.example.pinkrecycleradapter.core

import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.collection.ArrayMap
import androidx.databinding.ViewDataBinding
import com.example.pinkrecycleradapter.core.PinkViewHolderProvider.OnRepoChangeListener

@Suppress("unused")
open class BasePinkViewHolderProvider : PinkViewHolderProvider {

    private val typeIds = ArrayMap<Any, Int>()
    private val delegateBuilders = SparseArray<ViewHolderStub>()
    private val changeListeners = mutableListOf<OnRepoChangeListener>()

    override val count
        get() = delegateBuilders.size()

    override fun getItemType(item: Any): Int {
        if (item is Int) {
            if (delegateBuilders[item] != null) {
                return item
            }
        } else {
            return typeIds[item] ?: UNKNOWN_ITEM_TYPE
        }
        return UNKNOWN_ITEM_TYPE
    }

    override fun getViewHolder(parent: ViewGroup, viewType: Int): PinkViewHolder<*> {
        delegateBuilders[viewType]?.let {
            val inflater = LayoutInflater.from(parent.context)
            val binding =
                it.delegate.inflate(inflater = inflater, parent = parent, layoutId = it.layoutId)

            return when (binding) {
                is ViewDataBinding -> PinkViewHolder(
                    itemView = binding.root,
                    binding = binding,
                    delegate = it.delegate
                )
                is View -> PinkViewHolder(
                    itemView = binding,
                    binding = null,
                    delegate = it.delegate
                )
                else -> throw UnknownInflatedType
            }

        } ?: throw NoDelegateForTypeIdException
    }

    fun add(typeId: Any, layoutId: Int, delegate: PinkViewHolderDelegate<*>) =
        apply {
            val newTypeId = getNewTypeId()
            typeIds[typeId] = newTypeId
            delegateBuilders.put(newTypeId, ViewHolderStub(layoutId, delegate))
            notifyOnChange()
        }

    fun add(typeId: Int, layoutId: Int, delegate: PinkViewHolderDelegate<*>) =
        apply {
            typeId.takeIf { it > 0 } ?: throw TypeIdMustBeGreaterException
            delegateBuilders.put(typeId, ViewHolderStub(layoutId, delegate))
            notifyOnChange()
        }

    fun remove(typeId: Any) {
        typeIds[typeId]?.let {
            delegateBuilders.delete(it)
            notifyOnChange()
        }
    }

    fun remove(typeId: Int) {
        delegateBuilders.delete(typeId)
        notifyOnChange()
    }

    override fun registerOnChangeListener(listenerRepo: OnRepoChangeListener) {
        changeListeners.run { if (indexOf(listenerRepo) < 0) add(listenerRepo) }
    }

    override fun unregisterOnChangeListener(listenerRepo: OnRepoChangeListener) {
        changeListeners.remove(listenerRepo)
    }

    private fun getNewTypeId() =
        (typeIds.entries.maxByOrNull { it.value }?.value ?: Int.MIN_VALUE) + 1

    private fun notifyOnChange() {
        changeListeners.forEach { it.onViewHolderRepoChange(this) }
    }

    private data class ViewHolderStub(val layoutId: Int, val delegate: PinkViewHolderDelegate<*>)

    companion object {

        const val UNKNOWN_ITEM_TYPE = -1

        val UnknownInflatedType = Exception("Unknown inflated type")
        val NoDelegateForTypeIdException = Exception("No delegate for type id")
        val TypeIdMustBeGreaterException = Exception("Int type Id must be greater then 0")

    }

}