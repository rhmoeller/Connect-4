package com.example.robert.task04c

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.R.string.cancel
import android.content.DialogInterface
import android.text.InputType
import android.widget.EditText
import android.app.AlertDialog



class StartActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
    }

    fun numberDialog(text: String): Int {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Title")
        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_NUMBER
        builder.setView(input)

        var result: Int = 0
        builder.setPositiveButton("OK") { dialog, which ->
            result = which
        }
        builder.setNegativeButton("Cancel") { dialog, which ->
            result = -1
            dialog.cancel()
        }

        builder.show()
        return result
    }

    fun startGame(twoPlayerGame: Boolean = true) {
        startActivity(
            Intent(this, SettingsActivity::class.java).apply {
            putExtra("twoPlayerGame", twoPlayerGame)
        })
    }

    fun startOnePlayerGame(view: View) {
        startGame(false)
    }

    fun startTwoPlayerGame(view: View) {
        startGame(true)
    }
}
