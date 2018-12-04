package com.example.robert.task04c

import android.app.Activity
import android.os.Bundle

class GameActivity : Activity() {

    private var mGameView: GameView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val twoPlayerGame = intent.getBooleanExtra("twoPlayerGame",true)

        val columns = intent.getIntExtra("columns", 7)
        val rows = intent.getIntExtra("rows", 10)

        mGameView = GameView(this, GameLogic(columns, rows, twoPlayerGame))
        setContentView(mGameView)
    }
}
