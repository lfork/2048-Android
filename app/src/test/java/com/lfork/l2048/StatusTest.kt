package com.lfork.l2048

import org.junit.Test
import java.lang.Exception
import java.util.*

/**
 * Created by 98620 on 2018/10/10.
 */
class StatusTest {

    @Test
    fun getDimenFour() {
    }

    @Test
    fun getAsc() {
    }

    @Test
    fun printStatus() {
        GameControlPanel.start(4)

        var exit = false
        val input = Scanner(System.`in`)

        println("\n\nGame Tips: 0 exit, 1 move left, 2 right, 3 up, 4 down, other error")
        while (!exit) {
            try {
                print("\nInput your command: ")
                val cmd = input.next().toInt()
                when (cmd) {
                    1 -> GameControlPanel.moveLeft()
                    2 -> GameControlPanel.moveRight()
                    3 -> GameControlPanel.moveUp()
                    4 -> GameControlPanel.moveDown()
                    0 -> {
                        GameControlPanel.exit()
                        exit = true
                    }
                    else -> println("Input Error")

                }
            } catch (e: Exception) {
                e.printStackTrace()
                println("Input Error")
            }
        }
    }
}