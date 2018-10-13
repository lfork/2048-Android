package com.lfork.l2048.data

import android.util.Log

/**
 *
 * Created by 98620 on 2018/10/10.
 */
class SquareMatrix(val degree: Int){
    val matrix = Array(degree) { IntArray(degree) }

    private var matrixSize = 0

    var score = 0

    private val historyMatrix = Array(degree) { IntArray(degree) }
    private var historyScore = 0
    private var historyMatrixSize = 0

    fun revoke(){
        if (historyScore > 0) {
            score = historyScore
            matrixSize  = historyMatrixSize
            for (i in 0 until degree) {
                for (j in 0 until degree) {
                    matrix[i][j]=historyMatrix[i][j]
                }
            }
            historyScore = 0
            historyMatrixSize = 0
        }
    }

    fun saveHistory(){
        historyScore =  score
        historyMatrixSize   =  matrixSize
        for (i in 0 until degree) {
            for (j in 0 until degree) {
                historyMatrix[i][j] = matrix[i][j]
            }
        }
    }

    /**
     * 在矩阵上随机生成几个数字
     */
    init {
        generateRandomNumber()
        generateRandomNumber()
    }

    /**
     * 游戏刚开始移动完毕后随机生成数字
     */
    fun generateRandomNumber() {

        if (degree * degree - matrixSize == 0) {
            println("The Matrix is full!")
            return
        }
        var index = (Math.random() * 1000).toInt() % (degree * degree - matrixSize)
        for (i in 0 until degree) {
            for (j in 0 until degree) {
                if (matrix[i][j] == 0) {
                    if (index == 0) {
                        matrix[i][j] = 2
                        matrixSize++
                    }
                    index--
                }
            }
        }
    }

    /**
     * 2048游戏通过上下左右四个方向移动来合并相同数字，只有几种情况使游戏可以移动，要对这几种情况进行判定（以向左移动为例，其他情况一致）。
     * 某个为0的元素右边相邻位置不为0
     * 某个不为0的元素右边相邻位置与其相等
     */
    fun canMoveLeft(): Boolean {
        for (i in 0 until degree) {
            for (j in 0 until degree - 1) {
                if (matrix[i][j] > 0 && matrix[i][j] == matrix[i][j + 1]) {
                    return true
                }
                if (matrix[i][j] == 0 && matrix[i][j + 1] > 0) {
                    return true
                }
            }
        }
        println("Cannot move left!")
        return false
    }

    fun canMoveRight(): Boolean {
        for (i in 0 until degree) {
            for (j in 0 until degree - 1) {
                if (matrix[i][j] > 0 && matrix[i][j] == matrix[i][j + 1]) {
                    return true
                }
                if (matrix[i][j] > 0 && matrix[i][j + 1] == 0) {
                    return true
                }
            }
        }
        println("Cannot move right!")
        return false
    }

    fun canMoveUp(): Boolean {
        for (i in 0 until degree) {
            for (j in 0 until degree - 1) {
                if (matrix[j][i] > 0 && matrix[j][i] == matrix[j + 1][i]) {
                    return true
                }
                if (matrix[j][i] == 0 && matrix[j + 1][i] > 0) {
                    return true
                }
            }
        }

        println("Cannot move up!")
        return false
    }

    fun canMoveDown(): Boolean {
        for (i in 0 until degree) {
            for (j in 0 until degree - 1) {
                if (matrix[j][i] > 0 && matrix[j][i] == matrix[j + 1][i]) {
                    return true
                }
                if (matrix[j][i] > 0 && matrix[j + 1][i] == 0) {
                    return true
                }
            }
        }

        println("Cannot move down!")
        return false
    }


    fun doMoveLeft() {
        // 找出并保存非0元素的值和下标
        val tempArray = getHorizontalNoneZeroArray()
        // 向左移动
        for (i in 0 until degree) {
            for (j in 0 until degree) {
                if (tempArray[i].size > 0 && tempArray[i][0] > 0) {
                    matrix[i][j] = tempArray[i][0]
                    tempArray[i].removeAt(0)
                } else {
                    matrix[i][j] = 0
                }
            }
        }

    }

    fun doMoveRight() {
        // 找出并保存非0元素的值和下标
        val tempArray = getHorizontalNoneZeroArray()
        // 向右移动
        for (i in 0 until degree) {
            for (j in degree - 1 downTo 0) {
                if (tempArray[i].size > 0 && tempArray[i][0] > 0) {
                    matrix[i][j] = tempArray[i][0]
                    tempArray[i].removeAt(0)
                } else {
                    matrix[i][j] = 0
                }
            }
        }
    }

    fun doMoveUp() {
        // 找出并保存非0元素的值和下标
        val tempArray = getVerticalNoneZeroArray()
        // 向上移动
        for (i in 0 until degree) {
            for (j in 0 until degree) {
                if (tempArray[j].size > 0 && tempArray[j][0] > 0) {
                    matrix[i][j] = tempArray[j][0]
                    tempArray[j].removeAt(0)
                } else {
                    matrix[i][j] = 0
                }
            }
        }
    }

    fun doMoveDown() {
        // 找出并保存非0元素的值和下标
        val tempArray = getVerticalNoneZeroArray()
        // 向下移动
        for (i in degree - 1 downTo 0) {
            for (j in 0 until degree) {
                if (tempArray[j].size > 0 && tempArray[j][0] > 0) {
                    matrix[i][j] = tempArray[j][0]
                    tempArray[j].removeAt(0)
                } else {
                    matrix[i][j] = 0
                }
            }
        }
    }

    private fun getHorizontalNoneZeroArray(): Array<ArrayList<Int>> {
        val tempArray = Array(degree) { ArrayList<Int>(degree) }
        for (i in 0 until degree) {
            for (j in 0 until degree) {
                if (matrix[i][j] > 0) {
                    tempArray[i].add(matrix[i][j])
                }
            }
        }

        return tempArray
    }

    private fun getVerticalNoneZeroArray(): Array<ArrayList<Int>> {
        val tempArray = Array(degree) { ArrayList<Int>(degree) }
        for (i in 0 until degree) {
            for (j in 0 until degree) {
                if (matrix[i][j] > 0) {
                    tempArray[j].add(matrix[i][j])
                }
            }
        }

        return tempArray
    }



    fun mergeLeft() {
        for (i in 0 until degree) {
            for (j in 0 until degree - 1) {
                if (matrix[i][j] > 0 && matrix[i][j] == matrix[i][j + 1]) {
                    matrix[i][j] *= 2
                    score += matrix[i][j]
                    matrix[i][j + 1] = 0
                    matrixSize--
                }
            }
        }

    }

    fun mergeRight() {
        for (i in 0 until degree) {
            for (j in degree - 2 downTo 0) {
                if (matrix[i][j] > 0 && matrix[i][j] == matrix[i][j + 1]) {
                    matrix[i][j + 1] *= 2
                    score += matrix[i][j + 1]
                    matrix[i][j] = 0
                    matrixSize--
                }
            }
        }

    }

    fun mergeUp() {
        for (i in 0 until degree) {
            for (j in 0 until degree - 1) {
                if (matrix[j][i] > 0 && matrix[j][i] == matrix[j + 1][i]) {
                    matrix[j][i] *= 2
                    score += matrix[j][i]
                    matrix[j + 1][i] = 0
                    matrixSize--
                }
            }
        }

    }

    fun mergeDown() {
        for (i in 0 until degree) {
            for (j in degree - 2 downTo 0) {
                if (matrix[j][i] > 0 && matrix[j][i] == matrix[j + 1][i]) {
                    matrix[j + 1][i] *= 2
                    score += matrix[j + 1][i]
                    matrix[j][i] = 0
                    matrixSize--
                }
            }
        }
    }

    fun gameWinJude(): Boolean {
        for (i in 0 until degree) {
            if (matrix[i].max() == 2048) {
                return true
            }
        }
        return false
    }

    fun gameOverJudge(): Boolean {
        return !canMove()
    }

    private fun canMove(): Boolean {
        Log.d("eee", "canMoveLeft: " + canMoveLeft() +
                "\ncanMoveRight: " + canMoveRight() +
                "\ncanMoveUp: " + canMoveUp() +
                "\ncanMoveDown:" + canMoveDown())

        return canMoveLeft() || canMoveRight() || canMoveUp() || canMoveDown()
    }

    override fun toString(): String {
        val buffer = StringBuffer()

        for (e in matrix) {
            buffer.append("\n")
            for (i in e) {
                buffer.append(" $i")
            }
        }
        return buffer.toString()
    }
}

