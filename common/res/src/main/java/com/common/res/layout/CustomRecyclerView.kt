package com.common.res.layout

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView


class CustomRecyclerView : RecyclerView {
    private var mStartX = 0f
    private var mStartY = 0f

    constructor(context: Context) : super(context) {}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                mStartX = ev.x
                mStartY = ev.y
            }
            MotionEvent.ACTION_MOVE -> {
                val x = ev.x
                val y = ev.y
                val diffX = Math.abs(x - mStartX)
                val diffY = Math.abs(y - mStartY)
                return diffX <= diffY
            }

        }
        return super.onInterceptTouchEvent(ev)
    }
}
