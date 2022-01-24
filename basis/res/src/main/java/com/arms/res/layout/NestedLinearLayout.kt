package com.arms.res.layout

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.ViewConfiguration
import android.widget.LinearLayout
import androidx.core.view.*

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2021/08/11
 * desc   : 支持嵌套滚动的 LinearLayout
 */
class NestedLinearLayout @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle), NestedScrollingChild, NestedScrollingParent {
    private val mChildHelper: NestedScrollingChildHelper
    private val mParentHelper: NestedScrollingParentHelper
    private val mScrollConsumed = IntArray(2)
    private val mScrollOffset = IntArray(2)
    private val mTouchSlop: Float
    private val mMaximumVelocity: Float
    private val mMinimumVelocity: Float
    private var mLastMotionY = 0
    private var mActivePointerId = 0
    private var mVelocityTracker: VelocityTracker? = null
    private var mIsBeingDragged = false

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        val event = MotionEvent.obtain(ev)
        initVelocityTrackerIfNotExists()
        val actionMasked = event.actionMasked
        when (actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                mVelocityTracker!!.addMovement(ev)
                mLastMotionY = event.y.toInt()
                mActivePointerId = event.getPointerId(0)
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL)
            }
            MotionEvent.ACTION_MOVE -> {
                val activePointerIndex = event.findPointerIndex(mActivePointerId)
                if (activePointerIndex != -1) {
                    val y = event.getY(activePointerIndex).toInt()
                    var deltaY = mLastMotionY - y
                    if (dispatchNestedPreScroll(0, deltaY, mScrollConsumed, mScrollOffset)) {
                        deltaY -= mScrollConsumed[1]
                        event.offsetLocation(0f, mScrollOffset[1].toFloat())
                    }
                    if (!mIsBeingDragged && Math.abs(mLastMotionY - y) > mTouchSlop) {
                        val parent = parent
                        parent?.requestDisallowInterceptTouchEvent(true)
                        mIsBeingDragged = true
                        if (deltaY > 0) {
                            deltaY -= mTouchSlop.toInt()
                        } else {
                            deltaY += mTouchSlop.toInt()
                        }
                    }
                    if (mIsBeingDragged) {
                        mVelocityTracker!!.addMovement(ev)
                        mLastMotionY = y - mScrollOffset[1]
                        if (dispatchNestedScroll(0, 0, 0, deltaY, mScrollOffset)) {
                            mLastMotionY -= mScrollOffset[1]
                            event.offsetLocation(0f, mScrollOffset[1].toFloat())
                        }
                    }
                }
            }
            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                if (mIsBeingDragged) {
                    mVelocityTracker!!.computeCurrentVelocity(1000, mMaximumVelocity)
                    val initialVelocity = mVelocityTracker!!.getYVelocity(mActivePointerId)
                        .toInt()
                    if (Math.abs(initialVelocity) > mMinimumVelocity) {
                        flingWithNestedDispatch(-initialVelocity)
                    }
                }
                mActivePointerId = INVALID_POINTER
                endDrag()
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                val index = event.actionIndex
                mLastMotionY = event.getY(index).toInt()
                mActivePointerId = event.getPointerId(index)
            }
            MotionEvent.ACTION_POINTER_UP -> {
                onSecondaryPointerUp(event)
                mLastMotionY = event.getY(event.findPointerIndex(mActivePointerId)).toInt()
            }
            else -> {
            }
        }
        event.recycle()
        return true
    }

    private fun onSecondaryPointerUp(ev: MotionEvent) {
        val pointerIndex = (ev.action and MotionEvent.ACTION_POINTER_INDEX_MASK
                shr MotionEvent.ACTION_POINTER_INDEX_SHIFT)
        val pointerId = ev.getPointerId(pointerIndex)
        if (pointerId == mActivePointerId) {
            val newPointerIndex = if (pointerIndex == 0) 1 else 0
            mLastMotionY = ev.getY(newPointerIndex).toInt()
            mActivePointerId = ev.getPointerId(newPointerIndex)
            if (mVelocityTracker != null) {
                mVelocityTracker!!.clear()
            }
        }
    }

    private fun endDrag() {
        mIsBeingDragged = false
        recycleVelocityTracker()
        stopNestedScroll()
    }

    private fun flingWithNestedDispatch(velocityY: Int) {
        if (!dispatchNestedPreFling(0f, velocityY.toFloat())) {
            dispatchNestedFling(0f, velocityY.toFloat(), true)
        }
    }

    private fun initVelocityTrackerIfNotExists() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain()
        }
    }

    private fun recycleVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker!!.recycle()
            mVelocityTracker = null
        }
    }

    override fun requestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        if (disallowIntercept) {
            recycleVelocityTracker()
        }
        super.requestDisallowInterceptTouchEvent(disallowIntercept)
    }

    // NestedScrollingChild
    override fun setNestedScrollingEnabled(enabled: Boolean) {
        mChildHelper.isNestedScrollingEnabled = enabled
    }

    override fun isNestedScrollingEnabled(): Boolean {
        return mChildHelper.isNestedScrollingEnabled
    }

    override fun startNestedScroll(axes: Int): Boolean {
        return mChildHelper.startNestedScroll(axes)
    }

    override fun stopNestedScroll() {
        mChildHelper.stopNestedScroll()
    }

    override fun hasNestedScrollingParent(): Boolean {
        return mChildHelper.hasNestedScrollingParent()
    }

    override fun dispatchNestedScroll(
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        offsetInWindow: IntArray?
    ): Boolean {
        return mChildHelper.dispatchNestedScroll(
            dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnconsumed,
            offsetInWindow
        )
    }

    override fun dispatchNestedPreScroll(
        dx: Int,
        dy: Int,
        consumed: IntArray?,
        offsetInWindow: IntArray?
    ): Boolean {
        return mChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow)
    }

    override fun dispatchNestedFling(
        velocityX: Float,
        velocityY: Float,
        consumed: Boolean
    ): Boolean {
        return mChildHelper.dispatchNestedFling(velocityX, velocityY, consumed)
    }

    override fun dispatchNestedPreFling(velocityX: Float, velocityY: Float): Boolean {
        return mChildHelper.dispatchNestedPreFling(velocityX, velocityY)
    }

    // NestedScrollingParent
    override fun onStartNestedScroll(child: View, target: View, nestedScrollAxes: Int): Boolean {
        return nestedScrollAxes and ViewCompat.SCROLL_AXIS_VERTICAL != 0
    }

    override fun onNestedScrollAccepted(child: View, target: View, nestedScrollAxes: Int) {
        mParentHelper.onNestedScrollAccepted(child, target, nestedScrollAxes)
        startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL)
    }

    override fun onStopNestedScroll(target: View) {
        mParentHelper.onStopNestedScroll(target)
        stopNestedScroll()
    }

    override fun onNestedScroll(
        target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int,
        dyUnconsumed: Int
    ) {
        dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, null)
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray) {
        dispatchNestedPreScroll(dx, dy, consumed, null)
    }

    override fun onNestedFling(
        target: View,
        velocityX: Float,
        velocityY: Float,
        consumed: Boolean
    ): Boolean {
        return dispatchNestedFling(velocityX, velocityY, consumed)
    }

    override fun onNestedPreFling(target: View, velocityX: Float, velocityY: Float): Boolean {
        return dispatchNestedPreFling(velocityX, velocityY)
    }

    override fun getNestedScrollAxes(): Int {
        return mParentHelper.nestedScrollAxes
    }

    companion object {
        private const val INVALID_POINTER = -1
    }

    init {
        setWillNotDraw(false)
        mChildHelper = NestedScrollingChildHelper(this)
        mParentHelper = NestedScrollingParentHelper(this)
        isNestedScrollingEnabled = true
        mTouchSlop = ViewConfiguration.get(getContext()).scaledTouchSlop.toFloat()
        mMaximumVelocity = ViewConfiguration.get(getContext()).scaledMaximumFlingVelocity.toFloat()
        mMinimumVelocity = ViewConfiguration.get(getContext()).scaledMinimumFlingVelocity.toFloat()
    }
}