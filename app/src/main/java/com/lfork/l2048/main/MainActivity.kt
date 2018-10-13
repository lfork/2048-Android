package com.lfork.l2048.main

import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.DialogInterface
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.Toast
import com.lfork.l2048.*
import com.lfork.l2048.databinding.MainActBinding
import kotlinx.android.synthetic.main.main_act.*

class MainActivity : AppCompatActivity(),ViewNavigator {


    val TAG = "MainActivity"

    lateinit var binding: MainActBinding
    lateinit var viewModel: MainActivityViewModel
    private lateinit var dialog:AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_act)
        //这个有很多绑定方法，在Activity里面要用这个，不要记错了
        binding = DataBindingUtil.setContentView(this, R.layout.main_act)
        viewModel = MainActivityViewModel()
        viewModel.setNavigator(this)
        binding.viewModel = viewModel

        setupGridView()
        setupDialog()
        replay.setOnClickListener { viewModel.replay() }
        revoke.setOnClickListener { viewModel.revoke() }

    }

    private fun showDialog(msg:String){
        dialog.setMessage(msg)
        dialog.show()
    }

    private fun setupDialog(){
        // 1. Instantiate an AlertDialog.Builder with its constructor
        val builder = AlertDialog.Builder(this)

// 2. Chain together various setter methods to set the dialog characteristics
        builder.setTitle("Tips").setMessage("Game Over")
        builder.setPositiveButton("再玩儿一把", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface, id: Int) {
                // User clicked OK button
                dialog.dismiss()
                viewModel.replay()
            }
        })
        builder.setNegativeButton("不玩儿了", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface, id: Int) {
                // User cancelled the dialog
                System.exit(0)
            }
        })

// 3. Get the AlertDialog from create()
        dialog = builder.create()

    }

    private fun setupGridView() {
        matrix.adapter = MatrixAdapter(this)
    }

    override fun youWin() {
        showDialog("You Win !!")
    }

    override fun gameOver() {
        showDialog("Game Over")
    }


    /**
     * 触屏事件
     * @param event
     * @return
     */
    var downX = 0f
    var downY = 0f

    override fun onTouchEvent(event: MotionEvent): Boolean {
        var action = ""
        //在触发时回去到起始坐标
        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                //将按下时的坐标存储
                downX = x
                downY = y
                Log.e("Tag", "=======按下时X：$downX ")
                Log.e("Tag", "=======按下时Y：$downY")
            }
            MotionEvent.ACTION_UP -> {
                Log.e("Tag", "=======抬起时X：$x")
                Log.e("Tag", "=======抬起时Y：$y")

                Log.e("Tag", "=======downX检查：$downX ")
                Log.e("Tag", "=======downY检查：$downY")

                //获取到距离差
                val dx = x - downX
                val dy = y - downY

                Log.e("Tag", "=======dx:$dx ")
                Log.e("Tag", "=======dy:$dy")
                //防止是按下也判断
                if (Math.abs(dx) > 8 || Math.abs(dy) > 8) {
                    //通过距离差判断方向
                    val orientation = getOrientation(dx, dy)
                    when (orientation.toChar()) {
                        'r' -> {
                            action = "右"
                            viewModel.moveRight()
                        }
                        'l' -> {
                            action = "左"
                            viewModel.moveLeft()
                        }
                        'u' -> {
                            action = "上"
                            viewModel.moveUp()
                        }
                        'd' -> {
                            action = "下"
                            viewModel.moveDown()
                        }
                    }
//                    Toast.makeText(this, "向" + action + "滑动", Toast.LENGTH_SHORT).show()

                    Log.e("Tag","向" + action + "滑动" )
                }
            }
        }
        return super.onTouchEvent(event)
    }

    /**
     * 根据距离差判断 滑动方向
     * @param dx X轴的距离差
     * @param dy Y轴的距离差
     * @return 滑动的方向
     */
    private fun getOrientation(dx: Float, dy: Float): Int {
        Log.e("Tag", "========X轴距离差：$dx")
        Log.e("Tag", "========Y轴距离差：$dy")
        return if (Math.abs(dx) > Math.abs(dy)) {
            //X轴移动
            (if (dx > 0) 'r' else 'l').toInt()
        } else {
            //Y轴移动
            (if (dy > 0) 'd' else 'u').toInt()
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.onStart()
//        (matrix.adapter as MatrixAdapter).setItems(viewModel.matrixToList)
    }

    override fun onDestroy() {
        super.onDestroy()
        dialog.dismiss()
        viewModel.onDestroy()
    }


    /**
     * GridView的数据结构官方给的是一个数组，顺序显示的。但是我的数据源是矩阵，
     * 所以我这里就需要把矩阵转化为用于显示的数组
     */
    class MatrixAdapter(private val mContext: Context) : BaseAdapter() {

        val items = ArrayList<Int>(16)
        val views = ArrayList<TextView>(16)
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val textView: TextView
            if (convertView == null) {
                textView = TextView(mContext)
                if (items[position] > 0) {
                    textView.text = items[position].toString()

                } else {
                    textView.text = ""
                }

                textView.setTextColor(Color.WHITE)
                textView.textSize = px2sp(mContext, 24F)
                textView.setBackgroundColor(getColorByNumber(mContext, items[position]))
                textView.layoutParams = ViewGroup.LayoutParams(dp2px(mContext, 90F), dp2px(mContext, 90F))
                textView.gravity = Gravity.CENTER
                if (views.size <= 16)
                    views.add(textView)
            } else {
                textView = convertView as TextView
            }
            return textView
        }

        fun setItems(data: ArrayList<Int>) {
            items.clear()
            items.addAll(data)

            if (views.size > 0)
                for (i in 0 until 16) {
                    views[i].text = if (items[i] == 0) "" else items[i].toString()
                    views[i].setBackgroundColor(getColorByNumber(mContext, items[i]))
                }
        }


        override fun getItem(p0: Int): Any? = items[p0]
        override fun getItemId(position: Int): Long = position.toLong()
        override fun getCount(): Int = items.size


    }
}
