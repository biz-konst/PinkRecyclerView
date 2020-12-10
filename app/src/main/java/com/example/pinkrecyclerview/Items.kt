package com.example.pinkrecyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.pinkrecycleradapter.core.PinkViewHolderDelegate
import com.example.pinkrecycleradapter.core.PinkViewHolder
import com.example.pinkrecycleradapter.custom.PinkGroupNode
import com.example.pinkrecyclerview.databinding.Item1Binding
import com.example.pinkrecyclerview.databinding.Item2Binding
import com.example.pinkrecyclerview.databinding.Item3Binding
import com.example.pinkrecyclerview.databinding.SubItemBinding

data class ListItem(val title: String, val subTitle: String, val child: List<ChildLitItem>?)

data class ChildLitItem(val id: Int, val context: String)

fun createItems() = listOf(
    ListItem(
        "Item 1", "This is umbrella",
        listOf(
            ChildLitItem(1, "Big umbrella"),
            ChildLitItem(2, "Medium umbrella"),
            ChildLitItem(3, "Small umbrella"),
            ChildLitItem(4, "Big umbrella"),
            ChildLitItem(5, "Medium umbrella"),
            ChildLitItem(6, "Small umbrella"),
            ChildLitItem(7, "Big umbrella"),
            ChildLitItem(8, "Medium umbrella"),
            ChildLitItem(9, "Small umbrella")
        )
    ),
    ListItem(
        "Item 2", "This is drink",
        listOf(
            ChildLitItem(1, "It is water"),
            ChildLitItem(2, "It is apple juice"),
            ChildLitItem(3, "It is orange juice"),
            ChildLitItem(4, "It is jin"),
            ChildLitItem(5, "It is water"),
            ChildLitItem(6, "It is apple juice"),
            ChildLitItem(7, "It is orange juice"),
            ChildLitItem(8, "It is jin")
        )
    ),
    ListItem("Item 3", "This is milk", null)
)

class ListItemViewHolder1 : PinkViewHolderDelegate<PinkGroupNode<ListItem>> {

    override fun inflate(
        parent: ViewGroup,
        inflater: LayoutInflater,
        layoutId: Int
    ): Item1Binding = DataBindingUtil.inflate(inflater, layoutId, parent, false)

    override fun bind(
        holder: PinkViewHolder<PinkGroupNode<ListItem>>,
        item: PinkGroupNode<ListItem>,
        position: Int
    ) {
        (holder.binding as Item1Binding).apply {
            this.item = item.data
            this.adapter = item
        }
    }

}

class ListItemViewHolder2 : PinkViewHolderDelegate<PinkGroupNode<ListItem>> {

    override fun inflate(
        parent: ViewGroup,
        inflater: LayoutInflater,
        layoutId: Int
    ): Item2Binding = DataBindingUtil.inflate(inflater, layoutId, parent, false)

    override fun bind(
        holder: PinkViewHolder<PinkGroupNode<ListItem>>,
        item: PinkGroupNode<ListItem>,
        position: Int
    ) {
        (holder.binding as Item2Binding).apply {
            this.item = item.data
            this.adapter = item
        }
    }

}

class ListItemViewHolder3 : PinkViewHolderDelegate<PinkGroupNode<ListItem>> {

    override fun inflate(
        parent: ViewGroup,
        inflater: LayoutInflater,
        layoutId: Int
    ): Item3Binding = DataBindingUtil.inflate(inflater, layoutId, parent, false)

    override fun bind(
        holder: PinkViewHolder<PinkGroupNode<ListItem>>,
        item: PinkGroupNode<ListItem>,
        position: Int
    ) {
        (holder.binding as Item3Binding).apply {
            this.item = item.data
            this.adapter = item
        }
    }

}

class ChildListItemViewHolder : PinkViewHolderDelegate<ChildLitItem> {

    override fun inflate(
        parent: ViewGroup,
        inflater: LayoutInflater,
        layoutId: Int
    ): SubItemBinding = DataBindingUtil.inflate(inflater, layoutId, parent, false)

    override fun bind(
        holder: PinkViewHolder<ChildLitItem>,
        item: ChildLitItem,
        position: Int
    ) {
        (holder.binding as SubItemBinding).apply { this.item = item }
    }
}