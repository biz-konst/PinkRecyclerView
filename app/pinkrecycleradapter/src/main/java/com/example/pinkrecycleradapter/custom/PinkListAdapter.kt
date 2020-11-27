package com.example.pinkrecycleradapter.custom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.DiffUtil
import com.example.pinkrecycleradapter.core.PinkBaseAdapter
import com.example.pinkrecycleradapter.core.PinkViewHolderProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

@Suppress("unused")
open class PinkListAdapter<T>(
    provider: PinkViewHolderProvider,
    private val diffItemCallback: DiffUtil.ItemCallback<T>
) : PinkBaseAdapter<T>(provider) {

    private var items = listOf<T>()
    private val updateCallback by lazy { AdapterListUpdateCallback(this) }
    private val scope = GlobalScope

    private val _submitting = MutableLiveData<Boolean>()
    val submitting: LiveData<Boolean> = _submitting

    override fun getItem(position: Int): T = items[position]

    override fun getItemCount() = items.size

    fun submitList(list: List<T>?) {
        val prevList = items
        _submitting.value = true
        when {
            list.isNullOrEmpty() -> {
                items = Collections.emptyList()
                notifyDataSetChanged()
                _submitting.value = false
                onCurrentListChanged(prevList, items)
            }
            items.isEmpty() -> {
                items = Collections.unmodifiableList(list)
                notifyDataSetChanged()
                _submitting.value = false
                onCurrentListChanged(prevList, items)
            }
            else -> {
                scope.launch(Dispatchers.Main) {
                    AsyncDiffCallback(items, list, diffItemCallback).asyncCalculateDiff(true)
                        .also { items = Collections.unmodifiableList(list) }
                        .dispatchUpdatesTo(updateCallback)
                    _submitting.value = false
                    onCurrentListChanged(prevList, items)
                }
            }
        }
    }

    fun onCurrentListChanged(previousList: List<T>, currentList: List<T>) {}

}