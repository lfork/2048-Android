package com.lfork.l2048.main

import android.databinding.BaseObservable
import android.databinding.ObservableArrayList
import android.databinding.ObservableField
import android.text.TextUtils
import com.lfork.l2048.data.GameControlPanel
import com.lfork.l2048.data.GameController
import com.lfork.l2048.data.SquareMatrix

/**
 *
 * Created by 98620 on 2018/10/13.
 */
class MainActivityViewModel : GameController , BaseObservable() {


    val TAG = "MainActivityViewModel"

    var score = ObservableField<String>("")

    val matrixToList = ObservableArrayList<Int>()

    lateinit var squareMatrix: SquareMatrix

    private  var navigator: ViewNavigator? = null


    fun onStart() {
        if (TextUtils.isEmpty(score.get())) {
            GameControlPanel.start(4)
            squareMatrix = GameControlPanel.squareMatrix
        }
        refreshView()
    }

    private fun refreshView() {
        matrixToList.clear()
        for (i in 0 until squareMatrix.degree) {
            for (j in 0 until squareMatrix.degree) {
                matrixToList.add(squareMatrix.matrix[i][j])
            }
        }

        score.set("得分: ${GameControlPanel.score}" )

        if (GameControlPanel.isGameOver){
            navigator?.gameOver()
        }

        if (GameControlPanel.isWon){
            navigator?.youWin()
        }
    }

    fun onDestroy() {
        navigator = null
    }

    override fun moveLeft() {
        GameControlPanel.moveLeft()
        refreshView()
    }

    override fun moveRight() {
        GameControlPanel.moveRight()
        refreshView()
    }

    override fun moveUp() {
        GameControlPanel.moveUp()
        refreshView()
    }

    override fun moveDown() {
        GameControlPanel.moveDown()
        refreshView()
    }

    override fun revoke() {
        GameControlPanel.revoke()
        refreshView()
    }

    override fun exit() {
        refreshView()
    }

    override fun replay() {
        GameControlPanel.replay()
        score.set("")
        squareMatrix = GameControlPanel.squareMatrix
        refreshView()
    }

    fun setNavigator(viewNavigator: ViewNavigator) {
        navigator = viewNavigator
    }



}