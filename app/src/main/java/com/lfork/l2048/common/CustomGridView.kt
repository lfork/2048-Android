package com.lfork.l2048.common

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.GridView

/**
 *
 * Created by 98620 on 2018/10/13.
 */
class CustomGridView(context: Context?, attrs: AttributeSet?) : GridView(context, attrs) {

    /**
     * 设置上下不滚动
     */
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
       return false
    }
}