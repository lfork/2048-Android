package com.lfork.l2048

/**
 *
 * Created by 98620 on 2018/10/11.
 */
object GameControlPanel {
    private lateinit var squareMatrix: SquareMatrix;

    private var tempModel = 3

    var score = 0

    var isOver = false

    fun start(model: Int) {
        squareMatrix = SquareMatrix(model)
        tempModel = model
        println(squareMatrix)
    }

    fun moveLeft() {
        if (!isOver && squareMatrix.canMoveLeft()) {
            squareMatrix.doMoveLeft()
            squareMatrix.mergeLeft()
            squareMatrix.doMoveLeft()
            afterMove()
        }
    }

    fun moveRight() {
        if (!isOver && squareMatrix.canMoveRight()) {
            squareMatrix.doMoveRight()
            squareMatrix.mergeRight()
            squareMatrix.doMoveRight()
            afterMove()
        }
    }

    fun moveUp() {
        if (!isOver && squareMatrix.canMoveUp()) {
            squareMatrix.doMoveUp()
            squareMatrix.mergeUp()
            squareMatrix.doMoveUp()
            afterMove()
        }
    }

    fun moveDown() {
        if (!isOver && squareMatrix.canMoveDown()) {
            squareMatrix.doMoveDown()
            squareMatrix.mergeDown()
            squareMatrix.doMoveDown()
            afterMove()
        }
    }

    fun pause() {
    }

    fun resume() {
    }


    fun replay() {
        start(tempModel)
        score = 0
        isOver = false
    }

    private fun gameOver() {
        println("Game Over");
    }

    private fun wonTheGame() {
        println("You Win");
    }

    fun exit() {

    }

    private fun afterMove() {
        squareMatrix.generateRandomNumber()
        score = squareMatrix.score
        isOver = when {
            squareMatrix.gameWinJude() -> {
                wonTheGame()
                true
            }
            squareMatrix.gameOverJudge() -> {
                gameOver()
                true
            }
            else -> false
        }


        println("Score:$score  " +
                "\n$squareMatrix " +
                "\n\nisOver:$isOver")
    }


}