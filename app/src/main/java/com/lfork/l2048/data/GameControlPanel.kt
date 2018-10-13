package com.lfork.l2048.data

/**
 *
 * Created by 98620 on 2018/10/11.
 */
object GameControlPanel : GameController {
    lateinit var squareMatrix: SquareMatrix;

    private var tempModel = 4

    var score = 0

    var isFinished = false

    var isGameOver = false

    var isWon = false


    fun start(model: Int) {
        squareMatrix = SquareMatrix(model)
        tempModel = model
        println(squareMatrix)
    }

    fun start(model: Int, matrix: SquareMatrix) {
        squareMatrix = matrix
        tempModel = model
        println(squareMatrix)
    }

    override fun moveLeft() {
        if (!isFinished && squareMatrix.canMoveLeft()) {
            beforeMove()
            squareMatrix.doMoveLeft()
            squareMatrix.mergeLeft()
            squareMatrix.doMoveLeft()
            afterMove()

        }
    }

    override fun moveRight() {
        if (!isFinished && squareMatrix.canMoveRight()) {
            beforeMove()
            squareMatrix.doMoveRight()
            squareMatrix.mergeRight()
            squareMatrix.doMoveRight()
            afterMove()

        }
    }

    override fun moveUp() {
        if (!isFinished && squareMatrix.canMoveUp()) {
            beforeMove()
            squareMatrix.doMoveUp()
            squareMatrix.mergeUp()
            squareMatrix.doMoveUp()
            afterMove()
        }
    }

    override fun moveDown() {
        if (!isFinished && squareMatrix.canMoveDown()) {
            beforeMove()
            squareMatrix.doMoveDown()
            squareMatrix.mergeDown()
            squareMatrix.doMoveDown()
            afterMove()
        }
    }

    override fun revoke() {
        squareMatrix.revoke()
    }

    override fun exit() {
    }

    override fun replay() {
        start(tempModel)
        score = 0
        isFinished = false
        isGameOver = false
        isWon = false
    }

    private fun gameOver() {
        println("Game Over");
    }

    private fun wonTheGame() {
        println("You Win");
    }


    private fun beforeMove(){
        squareMatrix.saveHistory()
    }

    private fun afterMove() {
        squareMatrix.generateRandomNumber()
        score = squareMatrix.score
        isFinished = when {
            squareMatrix.gameWinJude() -> {
                wonTheGame()
                isWon = true
                true
            }
            squareMatrix.gameOverJudge() -> {
                gameOver()
                isGameOver = true
                true
            }
            else -> false
        }

        println("Score:$score  " +
                "\n$squareMatrix " +
                "\n\nisFinished:$isFinished")


    }
}