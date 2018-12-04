package com.example.robert.task04c

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Toast

class GameView(context: Context,val mGame: GameLogic) : View(context) {
    var mGestureDetector: GestureDetector
        private set
    private var mPlayer1Paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mPlayer2Paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mBGPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        initialise()
        mGestureDetector = GestureDetector(context, MyGestureListener())
    }

    private fun initialise() {
        mPlayer1Paint.style = Paint.Style.FILL
        mPlayer1Paint.color = Color.RED
        mPlayer2Paint.style = Paint.Style.FILL
        mPlayer2Paint.color = Color.BLUE
        mBGPaint.style = Paint.Style.FILL
        mBGPaint.color = Color.GRAY

    }

    var canvasWidth: Float = 0f
    var canvasHeight: Float = 0f

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val SEPARATOR_RATIO = 0.2
        val diameterX: Float
        val diameterY: Float
        val chosenDiameter: Float
        val separatorSize: Float
        val rowCount: Int = mGame!!.rows
        val colCount: Int = mGame!!.columns
        val noOfColSeparators: Int
        val noOfRowSeparators: Int
        var tokenAtPos: Int
        var paint: Paint

        noOfColSeparators = colCount + 1
        noOfRowSeparators = rowCount + 1

        diameterX = (getWidth() / (colCount + noOfColSeparators * SEPARATOR_RATIO)).toFloat()
        diameterY = (getHeight() / (rowCount + noOfRowSeparators * SEPARATOR_RATIO)).toFloat() // TODO: Is this correct?

        // Choose the smallest of the two and save that in the variable chosenDiameter
        if (diameterX < diameterY)
            chosenDiameter = diameterX
        else
            chosenDiameter = diameterY
        separatorSize = (chosenDiameter * SEPARATOR_RATIO).toFloat()

        // Based on the chosenDiameter, calculate the size of the GameBoard
        canvasWidth = noOfColSeparators * chosenDiameter * SEPARATOR_RATIO.toFloat() +
                colCount * chosenDiameter
        canvasHeight = noOfRowSeparators * chosenDiameter * SEPARATOR_RATIO.toFloat() +
                rowCount * chosenDiameter // TODO: Is this correct?

        // Draw the game board
//        canvas?.drawRect(0.toFloat(), 0.toFloat(), width.toFloat(), height.toFloat(), mGridPaint)
//        canvas?.drawRect(0.toFloat(), 0.toFloat(), canvasWidth, canvasHeight, mGridPaint)

        val radius = chosenDiameter / 2
        // Draw the circles on the game board
        for (col in 0 until colCount) {
            for (row in 0 until rowCount) {
                // Does the game array contain a piece at this location?
                tokenAtPos = mGame!!.getToken(col, row)
                // Choose the correct colour for the circle
                if (tokenAtPos == 1) {
                    paint = mPlayer1Paint
                } else if (tokenAtPos == 2) {
                    paint = mPlayer2Paint
                } else {
                    paint = mBGPaint
                }

                val cy = ((height/2) - (canvasHeight/2)) + (separatorSize + (chosenDiameter + separatorSize) * row + radius)
                val cx = ((width / 2) - (canvasWidth / 2))+ separatorSize + (chosenDiameter + separatorSize) * col + radius
                canvas?.drawCircle(cx, cy, radius, paint)
            }
        }
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        val r = this.mGestureDetector.onTouchEvent(ev)
        return super.onTouchEvent(ev) || r
    }


    internal inner class MyGestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(ev: MotionEvent): Boolean {
            return true
        }

        override fun onSingleTapUp(ev: MotionEvent): Boolean {
            if (ev.x in ((width / 2) - (canvasWidth / 2)) .. ((width / 2) + (canvasWidth / 2))) {
                val targetColumn = Math.floor(((
                        (ev.x - ((width / 2) - (canvasWidth / 2)))
                                / canvasWidth) * mGame.columns)
                        .toDouble()
                ).toInt()

                if (!mGame.takeTurn(targetColumn)) {
                    Toast.makeText(context, "Invalid move..", Toast.LENGTH_LONG).show()
                }

                val winner = mGame.lookForWinner()
                if (winner > 0) {
                    val color = if (winner == 1) "Red" else "Blue"
                    context.startActivity(Intent(context, EndActivity::class.java).apply {
                        if (mGame.twoPlayerGame)
                            putExtra("event", "$color player won!")
                        else {
                            if (winner == 1) putExtra("event", "You won!")
                            else putExtra("event", "You lost..")
                        }
                    })
                }

                if (!mGame.hasAvailableMoves()) {
                    context.startActivity(Intent(context, EndActivity::class.java).apply {
                        putExtra("event", "You filled the entire board..")
                    })
                }


                invalidate()
            }
            return true
        }
    }
}