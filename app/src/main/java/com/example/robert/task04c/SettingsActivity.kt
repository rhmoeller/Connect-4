package com.example.robert.task04c

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
    }

    fun startBtnPressed(view: View) {
        startActivity(Intent(this,GameActivity::class.java).apply {
            putExtra("twoPlayerGame", intent.getBooleanExtra("twoPlayerGame", true))
            putExtra("columns",columns.text.toString().toInt())
            putExtra("rows", rows.text.toString().toInt())
        })
    }
}
