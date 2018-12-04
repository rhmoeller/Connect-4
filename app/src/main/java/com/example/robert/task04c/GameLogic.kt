package com.example.robert.task04c

class GameLogic(val columns: Int, val rows: Int, val twoPlayerGame: Boolean = true) {
    private var mData: Array<IntArray> = Array(columns) { IntArray(rows) }
    private val gameComputer: GameComputer = GameComputer()
    private var playerTurn: Boolean = true

    fun getToken(column: Int, row: Int): Int {
        return mData[column][row]
    }

    fun lookForWinner(): Int {
        var horisontalStreak = 0
        var verticalStreak = 0
        var diagonalStreak = 0
        var player = 0
        var winner = 0

        // looking for a horizontal streak
        for (row in  0 until rows) {
            for (column in 0 until columns) {
                val token = getToken(column, row)
                if (token != player) {
                    player = token
                    horisontalStreak = 1
                } else if (token == player && player > 0) {
                    ++horisontalStreak
                }

                if (horisontalStreak == 4) {
                    winner = player
                    break
                }
            }
        }
        if (winner > 0) return winner
        player = 0
        // looking for a vertical streak
        for (column in 0 until columns) {
            for (row in 0 until rows) {
                val token = getToken(column, row)
                if (token != player) {
                    player = token
                    verticalStreak = 1
                } else if (token == player && player > 0) {
                    ++verticalStreak
                }

                if (verticalStreak == 4) {
                    winner = player
                    break
                }
            }
        }
        if (winner > 0) return winner

        // looking for a diagonal streak
        var leftDiagonal = false
        var rightDiagonal = false
        for (row in 0 until rows) {
            for (column in 0 until columns) {
                val token = getToken(column, row)
                if (token != 0) {
                    player = token
                    leftDiagonal = diagonalStreak(column, row, player, -1)
                    rightDiagonal = diagonalStreak(column, row, player, 1)
                    if (leftDiagonal || rightDiagonal) {
                        winner = player
                        break
                    }

                }

            }
        }

        return winner
    }

    fun diagonalStreak(column: Int, row: Int, player: Int, direction: Int = 1, streak: Int = 1): Boolean {
        val token = getToken(column, row)
        val isWinning = streak >= 4
        if (token != player) return streak - 1 >= 4
        if (isWinning) return isWinning

        if (direction < 0) {
            if (column >= 1 && row < rows - 1) { // LEFT
                if (diagonalStreak(column - 1, row + 1, player, direction, streak + 1)) return true
            }
        } else {
            if (column < columns - 1 && row < rows - 1) { // RIGHT
                if (diagonalStreak(column + 1, row + 1, player, direction, streak + 1)) return true
            }
        }
        println()

        return false
    }

    fun takeTurn(column: Int): Boolean {
        var validMove = playToken(column, if (playerTurn) 1 else 2)
        if (validMove) {
            if (twoPlayerGame) playerTurn = !playerTurn
            else if (hasAvailableMoves()) playToken(gameComputer.pickColumn(mData), 2)
        }
        return validMove
    }

    fun playToken(column: Int, player: Int): Boolean {
        if (player <= 0) {
            throw IllegalArgumentException("Player numbers start with 1")
        }

        for (row in 0 until rows) {
            if (mData[column][rows - 1 - row] === 0) {
                mData[column][rows - 1 - row] = player
                return true
            }
        }
        return false // illegal move
    }

    fun hasAvailableMoves(): Boolean {
        var movesLeft = 0
        for (column in mData)
            for (item in column)
                if (item == 0) ++movesLeft
        return movesLeft > 0
    }
}


