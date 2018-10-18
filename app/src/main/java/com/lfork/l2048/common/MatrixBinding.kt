package com.lfork.l2048.common

import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.GridView
import android.widget.ImageView
import com.lfork.l2048.main.MainActivity.MatrixAdapter

/**
 * 通用的 ListView和RecyclerView的数据绑定
 *
 * @author 98620
 * @date 2018/10/13
 */
object MatrixBinding {
}

/**
 * 注解的参数和函数的参数要匹配
 */
@BindingAdapter("matrixToList")
fun setMatrixItems(gridView: GridView, matrixToList: ArrayList<Int>) {
    Log.d("", "还是没有加载么？")
    val adapter = gridView.adapter as MatrixAdapter
    adapter.setItems(matrixToList)
    adapter.notifyDataSetInvalidated()
    adapter.notifyDataSetChanged()
}
