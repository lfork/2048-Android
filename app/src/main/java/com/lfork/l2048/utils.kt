package com.lfork.l2048

import android.content.Context
import android.util.TypedValue
import java.security.AccessControlContext

/**
 *
 * Created by 98620 on 2018/10/13.
 */

fun getColorByNumber(context: Context, value: Int): Int = when (value) {
    0 -> context.resources.getColor(R.color.number0)
    2 -> context.resources.getColor(R.color.number2)
    4 -> context.resources.getColor(R.color.number4)
    8 -> context.resources.getColor(R.color.number8)
    16 -> context.resources.getColor(R.color.number16)
    32 -> context.resources.getColor(R.color.number32)
    64 -> context.resources.getColor(R.color.number64)
    128 -> context.resources.getColor(R.color.number128)
    256 -> context.resources.getColor(R.color.number256)
    512 -> context.resources.getColor(R.color.number512)
    1024 -> context.resources.getColor(R.color.number1024)
    2048 -> context.resources.getColor(R.color.number2048)
    else -> context.resources.getColor(R.color.number4096)
}

fun px2dp(context: Context, pxValue: Float): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pxValue, context.resources.displayMetrics)
}

fun px2sp(context: Context, pxValue: Float): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, pxValue, context.resources.displayMetrics)
}

fun dp2px(context: Context, dpValue: Float): Int {
    val scale = context.resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}

fun sp2px(context: Context, spValue: Float): Int {
    val fontScale = context.resources.displayMetrics.scaledDensity
    return (spValue * fontScale + 0.5f).toInt()
}
