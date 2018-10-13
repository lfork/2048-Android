package com.lfork.l2048.data

/**
 *
 * Created by 98620 on 2018/10/13.
 */
interface GameController {

    fun moveLeft()
    fun moveRight()
    fun moveUp()
    fun moveDown()
    fun revoke()
    fun replay()
    fun exit()

}