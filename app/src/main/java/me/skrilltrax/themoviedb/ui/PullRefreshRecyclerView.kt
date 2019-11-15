///*
// * Copyright (C) 2013 The Android Open Source Project
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package me.skrilltrax.themoviedb.ui
//
//import android.content.Context
//import android.graphics.Canvas
//import android.util.AttributeSet
//import android.view.MotionEvent
//import android.view.View
//import android.view.ViewConfiguration
//import android.view.ViewGroup
//import android.view.animation.AccelerateInterpolator
//import android.view.animation.Animation
//import android.view.animation.Animation.AnimationListener
//import android.view.animation.DecelerateInterpolator
//import android.view.animation.Transformation
//import android.widget.AbsListView
//
//import androidx.core.view.ViewCompat
//
///**
// * The SwipeRefreshLayout should be used whenever the user can refresh the
// * contents of a view via a vertical swipe gesture. The activity that
// * instantiates this view should add an OnRefreshListener to be notified
// * whenever the swipe to refresh gesture is completed. The SwipeRefreshLayout
// * will notify the listener each and every time the gesture is completed again;
// * the listener is responsible for correctly determining when to actually
// * initiate a refresh of its content. If the listener determines there should
// * not be a refresh, it must call setRefreshing(false) to cancel any visual
// * indication of a refresh. If an activity wishes to show just the progress
// * animation, it should call setRefreshing(true). To disable the gesture and progress
// * animation, call setEnabled(false) on the view.
// *
// *
// *  This layout should be made the parent of the view that will be refreshed as a
// * result of the gesture and can only support one direct child. This view will
// * also be made the target of the gesture and will be forced to match both the
// * width and the height supplied in this layout. The SwipeRefreshLayout does not
// * provide accessibility events; instead, a menu item must be provided to allow
// * refresh of the content wherever this gesture is used.
// */
//class SwipeRefreshLayout
///**
// * Constructor that is called when inflating SwipeRefreshLayout from XML.
// * @param context
// * @param attr
// */
//@JvmOverloads constructor(context: Context, attr: AttributeSet? = null) : ViewGroup(context, attr) {
////    private val mProgressBar: SwipeProgressBar? //the thing that shows progress is going
//    private var mTarget: View? = null //the content that gets pulled down
//    private var mOriginalOffsetTop: Int = 0
//    private var mListener: OnRefreshListener? = null
//    private var mDownEvent: MotionEvent? = null
//    private var mFrom: Int = 0
//    /**
//     * @return Whether the SwipeRefreshWidget is actively showing refresh
//     * progress.
//     */
//    /**
//     * Notify the widget that refresh state has changed. Do not call this when
//     * refresh is triggered by a swipe gesture.
//     *
//     * @param refreshing Whether or not the view should show refresh progress.
//     */
//    var isRefreshing = false
//        set(refreshing) {
//            if (isRefreshing != refreshing) {
//                ensureTarget()
//                mCurrPercentage = 0f
//                field = refreshing
//                if (isRefreshing) {
//                    mProgressBar!!.start()
//                } else {
//                    mProgressBar!!.stop()
//                }
//            }
//        }
//    private val mTouchSlop: Int
//    private var mDistanceToTriggerSync = -1f
//    private var mPrevY: Float = 0.toFloat()
//    private val mMediumAnimationDuration: Int
//    private var mFromPercentage = 0f
//    private var mCurrPercentage = 0f
//    private val mProgressBarHeight: Int
//    private var mCurrentTargetOffsetTop: Int = 0
//    // Target is returning to its start offset because it was cancelled or a
//    // refresh was triggered.
//    private var mReturningToStart: Boolean = false
//    private val mDecelerateInterpolator: DecelerateInterpolator
//    private val mAccelerateInterpolator: AccelerateInterpolator
//    private val mAnimateToStartPosition = object : Animation() {
//        public override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
//            var targetTop = 0
//            if (mFrom != mOriginalOffsetTop) {
//                targetTop = mFrom + ((mOriginalOffsetTop - mFrom) * interpolatedTime).toInt()
//            }
//            var offset = targetTop - mTarget!!.top
//            val currentTop = mTarget!!.top
//            if (offset + currentTop < 0) {
//                offset = 0 - currentTop
//            }
//            setTargetOffsetTopAndBottom(offset)
//        }
//    }
//    private val mShrinkTrigger = object : Animation() {
//        public override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
//            val percent = mFromPercentage + (0 - mFromPercentage) * interpolatedTime
////            mProgressBar!!.setTriggerPercentage(percent)
//        }
//    }
//    private val mReturnToStartPositionListener = object : BaseAnimationListener() {
//        override fun onAnimationEnd(animation: Animation) {
//            // Once the target content has returned to its start position, reset
//            // the target offset to 0
//            mCurrentTargetOffsetTop = 0
//        }
//    }
//    private val mShrinkAnimationListener = object : BaseAnimationListener() {
//        override fun onAnimationEnd(animation: Animation) {
//            mCurrPercentage = 0f
//        }
//    }
//    private val mReturnToStartPosition = Runnable {
//        mReturningToStart = true
//        animateOffsetToStartPosition(
//            mCurrentTargetOffsetTop + paddingTop,
//            mReturnToStartPositionListener
//        )
//    }
//    // Cancel the refresh gesture and animate everything back to its original state.
//    private val mCancel = Runnable {
//        mReturningToStart = true
//        // Timeout fired since the user last moved their finger; animate the
//        // trigger to 0 and put the target back at its original position
//        if (mProgressBar != null) {
//            mFromPercentage = mCurrPercentage
//            mShrinkTrigger.duration = mMediumAnimationDuration.toLong()
//            mShrinkTrigger.setAnimationListener(mShrinkAnimationListener)
//            mShrinkTrigger.reset()
//            mShrinkTrigger.interpolator = mDecelerateInterpolator
//            startAnimation(mShrinkTrigger)
//        }
//        animateOffsetToStartPosition(
//            mCurrentTargetOffsetTop + paddingTop,
//            mReturnToStartPositionListener
//        )
//    }
//
//    init {
//        mTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
//        mMediumAnimationDuration = resources.getInteger(
//            android.R.integer.config_mediumAnimTime
//        )
//        setWillNotDraw(false)
////        mProgressBar = SwipeProgressBar(this)
//        val metrics = resources.displayMetrics
//        mProgressBarHeight = (metrics.density * PROGRESS_BAR_HEIGHT).toInt()
//        mDecelerateInterpolator = DecelerateInterpolator(DECELERATE_INTERPOLATION_FACTOR)
//        mAccelerateInterpolator = AccelerateInterpolator(ACCELERATE_INTERPOLATION_FACTOR)
//    }
//
//    public override fun onAttachedToWindow() {
//        super.onAttachedToWindow()
//        removeCallbacks(mCancel)
//        removeCallbacks(mReturnToStartPosition)
//    }
//
//    public override fun onDetachedFromWindow() {
//        super.onDetachedFromWindow()
//        removeCallbacks(mReturnToStartPosition)
//        removeCallbacks(mCancel)
//    }
//
//    private fun animateOffsetToStartPosition(from: Int, listener: AnimationListener) {
//        mFrom = from
//        mAnimateToStartPosition.reset()
//        mAnimateToStartPosition.duration = mMediumAnimationDuration.toLong()
//        mAnimateToStartPosition.setAnimationListener(listener)
//        mAnimateToStartPosition.interpolator = mDecelerateInterpolator
//        mTarget!!.startAnimation(mAnimateToStartPosition)
//    }
//
//    /**
//     * Set the listener to be notified when a refresh is triggered via the swipe
//     * gesture.
//     */
//    fun setOnRefreshListener(listener: OnRefreshListener) {
//        mListener = listener
//    }
//
//    private fun setTriggerPercentage(percent: Float) {
//        if (percent == 0f) {
//            // No-op. A null trigger means it's uninitialized, and setting it to zero-percent
//            // means we're trying to reset state, so there's nothing to reset in this case.
//            mCurrPercentage = 0f
//            return
//        }
//        mCurrPercentage = percent
////        mProgressBar!!.setTriggerPercentage(percent)
//    }
//
//    /**
//     * Set the four colors used in the progress animation. The first color will
//     * also be the color of the bar that grows in response to a user swipe
//     * gesture.
//     *
//     * @param colorRes1 Color resource.
//     * @param colorRes2 Color resource.
//     * @param colorRes3 Color resource.
//     * @param colorRes4 Color resource.
//     */
//    fun setColorScheme(colorRes1: Int, colorRes2: Int, colorRes3: Int, colorRes4: Int) {
//        ensureTarget()
//        val res = resources
//        val color1 = res.getColor(colorRes1)
//        val color2 = res.getColor(colorRes2)
//        val color3 = res.getColor(colorRes3)
//        val color4 = res.getColor(colorRes4)
////        mProgressBar!!.setColorScheme(color1, color2, color3, color4)
//    }
//
//    private fun ensureTarget() {
//        // Don't bother getting the parent height if the parent hasn't been laid out yet.
//        if (mTarget == null) {
//            check(!(childCount > 1 && !isInEditMode)) { "SwipeRefreshLayout can host only one direct child" }
//            mTarget = getChildAt(0)
//            mOriginalOffsetTop = mTarget!!.top + paddingTop
//        }
//        if (mDistanceToTriggerSync == -1f) {
//            if (parent != null && (parent as View).height > 0) {
//                val metrics = resources.displayMetrics
//                mDistanceToTriggerSync = Math.min(
//                    (parent as View).height * MAX_SWIPE_DISTANCE_FACTOR,
//                    REFRESH_TRIGGER_DISTANCE * metrics.density
//                ).toInt().toFloat()
//            }
//        }
//    }
//
//    override fun draw(canvas: Canvas) {
//        super.draw(canvas)
////        mProgressBar!!.draw(canvas)
//    }
//
//    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
//        val width = measuredWidth
//        val height = measuredHeight
////        mProgressBar!!.setBounds(0, 0, width, mProgressBarHeight)
//        if (childCount == 0) {
//            return
//        }
//        val child = getChildAt(0)
//        val childLeft = paddingLeft
//        val childTop = mCurrentTargetOffsetTop + paddingTop
//        val childWidth = width - paddingLeft - paddingRight
//        val childHeight = height - paddingTop - paddingBottom
//        child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight)
//    }
//
//    public override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
//        check(!(childCount > 1 && !isInEditMode)) { "SwipeRefreshLayout can host only one direct child" }
//        if (childCount > 0) {
//            getChildAt(0).measure(
//                View.MeasureSpec.makeMeasureSpec(
//                    measuredWidth - paddingLeft - paddingRight,
//                    View.MeasureSpec.EXACTLY
//                ),
//                View.MeasureSpec.makeMeasureSpec(
//                    measuredHeight - paddingTop - paddingBottom,
//                    View.MeasureSpec.EXACTLY
//                )
//            )
//        }
//    }
//
//    /**
//     * @return Whether it is possible for the child view of this layout to
//     * scroll up. Override this if the child view is a custom view.
//     */
//    fun canChildScrollUp(): Boolean {
//        if (android.os.Build.VERSION.SDK_INT < 14) {
//            if (mTarget is AbsListView) {
//                val absListView = mTarget as AbsListView?
//                return absListView!!.childCount > 0 && (absListView.firstVisiblePosition > 0 || absListView.getChildAt(
//                    0
//                )
//                    .top < absListView.paddingTop)
//            } else {
//                return mTarget!!.scrollY > 0
//            }
//        } else {
//            return ViewCompat.canScrollVertically(mTarget!!, -1)
//        }
//    }
//
//    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
//        ensureTarget()
//        var handled = false
//        if (mReturningToStart && ev.action == MotionEvent.ACTION_DOWN) {
//            mReturningToStart = false
//        }
//        if (isEnabled && !mReturningToStart && !canChildScrollUp()) {
//            handled = onTouchEvent(ev)
//        }
//        return if (!handled) super.onInterceptTouchEvent(ev) else handled
//    }
//
//    override fun requestDisallowInterceptTouchEvent(b: Boolean) {
//        // Nope.
//    }
//
//    override fun onTouchEvent(event: MotionEvent): Boolean {
//        val action = event.action
//        var handled = false
//        when (action) {
//            MotionEvent.ACTION_DOWN -> {
//                mCurrPercentage = 0f
//                mDownEvent = MotionEvent.obtain(event)
//                mPrevY = mDownEvent!!.y
//            }
//            MotionEvent.ACTION_MOVE -> if (mDownEvent != null && !mReturningToStart) {
//                val eventY = event.y
//                val yDiff = eventY - mDownEvent!!.y
//                if (yDiff > mTouchSlop) {
//                    // User velocity passed min velocity; trigger a refresh
//                    if (yDiff > mDistanceToTriggerSync) {
//                        // User movement passed distance; trigger a refresh
//                        startRefresh()
//                        handled = true
//                    } else {
//                        // Just track the user's movement
//                        setTriggerPercentage(
//                            mAccelerateInterpolator.getInterpolation(
//                                yDiff / mDistanceToTriggerSync
//                            )
//                        )
//                        var offsetTop = yDiff
//                        if (mPrevY > eventY) {
//                            offsetTop = yDiff - mTouchSlop
//                        }
//                        updateContentOffsetTop(offsetTop.toInt())
//                        if (mPrevY > eventY && mTarget!!.top < mTouchSlop) {
//                            // If the user puts the view back at the top, we
//                            // don't need to. This shouldn't be considered
//                            // cancelling the gesture as the user can restart from the top.
//                            removeCallbacks(mCancel)
//                        } else {
//                            updatePositionTimeout()
//                        }
//                        mPrevY = event.y
//                        handled = true
//                    }
//                }
//            }
//            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> if (mDownEvent != null) {
//                mDownEvent!!.recycle()
//                mDownEvent = null
//            }
//        }
//        return handled
//    }
//
//    private fun startRefresh() {
//        removeCallbacks(mCancel)
//        mReturnToStartPosition.run()
//        isRefreshing = true
//        mListener!!.onRefresh()
//    }
//
//    private fun updateContentOffsetTop(targetTop: Int) {
//        var targetTop = targetTop
//        val currentTop = mTarget!!.top
//        if (targetTop > mDistanceToTriggerSync) {
//            targetTop = mDistanceToTriggerSync.toInt()
//        } else if (targetTop < 0) {
//            targetTop = 0
//        }
//        setTargetOffsetTopAndBottom(targetTop - currentTop)
//    }
//
//    private fun setTargetOffsetTopAndBottom(offset: Int) {
//        mTarget!!.offsetTopAndBottom(offset)
//        mCurrentTargetOffsetTop = mTarget!!.top
//    }
//
//    private fun updatePositionTimeout() {
//        removeCallbacks(mCancel)
//        postDelayed(mCancel, RETURN_TO_ORIGINAL_POSITION_TIMEOUT)
//    }
//
//    /**
//     * Classes that wish to be notified when the swipe gesture correctly
//     * triggers a refresh should implement this interface.
//     */
//    interface OnRefreshListener {
//        fun onRefresh()
//    }
//
//    /**
//     * Simple AnimationListener to avoid having to implement unneeded methods in
//     * AnimationListeners.
//     */
//    private open inner class BaseAnimationListener : AnimationListener {
//        override fun onAnimationStart(animation: Animation) {}
//        override fun onAnimationEnd(animation: Animation) {}
//        override fun onAnimationRepeat(animation: Animation) {}
//    }
//
//    companion object {
//        private const val RETURN_TO_ORIGINAL_POSITION_TIMEOUT: Long = 300
//        private const val ACCELERATE_INTERPOLATION_FACTOR = 1.5f
//        private const val DECELERATE_INTERPOLATION_FACTOR = 2f
//        private const val PROGRESS_BAR_HEIGHT = 4f
//        private const val MAX_SWIPE_DISTANCE_FACTOR = .6f
//        private const val REFRESH_TRIGGER_DISTANCE = 120
//    }
//}
///**
// * Simple constructor to use when creating a SwipeRefreshLayout from code.
// * @param context
// */