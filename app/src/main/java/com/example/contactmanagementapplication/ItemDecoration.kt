package com.example.contactmanagementapplication

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

// class to add spacing between recyclerview items
class SpaceItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {

    // adjusts the bottom offset for each RecyclerView item
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = space // adds spacing at the bottom of each item
    }
}
