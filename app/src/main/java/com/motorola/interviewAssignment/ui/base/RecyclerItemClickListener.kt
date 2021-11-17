package com.motorola.interviewAssignment.ui.base

import android.content.Context
import android.view.MotionEvent

import androidx.recyclerview.widget.RecyclerView

import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.View
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener


class RecyclerItemClickListener(
    context: Context?,
    recyclerView: RecyclerView,
    private val mOnItemClickListener: OnItemClickListener?,
    private val mOnItemLongClickListener: OnItemLongClickListener?,
) : OnItemTouchListener {

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    interface OnItemLongClickListener{
        fun onItemLongClick(position: Int)
    }

    private var mGestureDetector: GestureDetector = GestureDetector(context, object : SimpleOnGestureListener() {
        override fun onSingleTapUp(e: MotionEvent): Boolean {
            return true
        }

        override fun onLongPress(e: MotionEvent) {
            val child: View? = recyclerView.findChildViewUnder(e.x, e.y)
            if (child != null && mOnItemLongClickListener != null) {
                mOnItemLongClickListener.onItemLongClick(recyclerView.getChildAdapterPosition(child))
            }
        }
    })

    override fun onInterceptTouchEvent(view: RecyclerView, e: MotionEvent): Boolean {
        val childView: View? = view.findChildViewUnder(e.x, e.y)
        if (childView != null && mOnItemClickListener != null && mGestureDetector.onTouchEvent(e)) {
            mOnItemClickListener.onItemClick(view.getChildAdapterPosition(childView))
            return true
        }
        return false
    }

    override fun onTouchEvent(view: RecyclerView, motionEvent: MotionEvent) {}
    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}

}