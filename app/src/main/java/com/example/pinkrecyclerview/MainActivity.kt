package com.example.pinkrecyclerview

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import androidx.recyclerview.widget.*
import com.example.pinkrecycleradapter.core.BasePinkViewHolderProvider
import com.example.pinkrecycleradapter.custom.PinkGroupAdapter
import com.example.pinkrecycleradapter.custom.PinkGroupNodeAdapter
import com.example.pinkrecycleradapter.custom.PinkListAdapter

class MainActivity : AppCompatActivity() {


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val items = createItems()

        val viewHolderRepo =
            BasePinkViewHolderProvider()
                .add(items[0].title, R.layout.item_1, ListItemViewHolder1())
                .add(items[1].title, R.layout.item_2, ListItemViewHolder2())
                .add(items[2].title, R.layout.item_3, ListItemViewHolder3())
                .add(ChildLitItem::class, R.layout.sub_item, ChildListItemViewHolder())

        val list = findViewById<RecyclerView>(R.id.list)
        list.adapter = PinkGroupAdapter(viewHolderRepo)
            .addNode(items[0], items[0].title,
                PinkListAdapter(viewHolderRepo, diffItemCallback)
                    .apply { submitList(items[0].child) })
            .addNode(items[1], items[1].title,
                PinkListAdapter(viewHolderRepo, diffItemCallback)
                    .apply { submitList(items[1].child) })
            .addNode(items[2], items[2].title,
                PinkListAdapter(viewHolderRepo, diffItemCallback)
                    .apply { submitList(items[1].child) })
//        list.itemAnimator?.apply {
//            addDuration = 0
//            removeDuration = 0
//        }

        list.addOnItemTouchListener(object : RecyclerView.SimpleOnItemTouchListener() {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                if (e.action == MotionEvent.ACTION_DOWN) {
                    rv.itemAnimator?.endAnimations()
                }
                return false
            }
        })

    }

    companion object {

        val diffItemCallback = object : DiffUtil.ItemCallback<ChildLitItem>() {
            override fun areItemsTheSame(oldItem: ChildLitItem, newItem: ChildLitItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ChildLitItem, newItem: ChildLitItem): Boolean {
                return oldItem == newItem
            }
        }

    }

}