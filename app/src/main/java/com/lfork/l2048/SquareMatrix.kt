package com.lfork.l2048

import java.util.ArrayList

/**
 *
 * Created by 98620 on 2018/10/10.
 */
class SquareMatrix(private val degree:Int) {
    private val matrix = ArrayList<IntArray>(degree)

    private var isGameOver = false

    private var score = 0


    init {

        for (i in 1..degree){
            val intArray = IntArray(degree)
            for (j in 1..degree) {
                intArray[j]=0
            }
            matrix.add(intArray)
        }
    }


    fun generateRandomNumber(){

    }

    private fun canMove(): Boolean {
        return false;
    }

    fun doMoveRight(){

    }

    fun doMoveLeft(){

    }

    fun doMoveUp(){

    }

    fun doMoveDown(){

    }

    fun mergeElements(){

    }

    fun gameWinJude():Boolean{
        return score >= 2048
    }

    fun gameOverJudge():Boolean{
        return  canMove()
    }




    override fun toString(): String {
        val buffer= StringBuffer()

        for (e in matrix) {
            buffer.append("\n")
            for (i in e) {
                buffer.append(" $i")
            }
        }
        return buffer.toString()
    }
}

