package com.example.robert.task04c

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_end.*

class EndActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end)
        endMessageTxt.text = intent.getStringExtra("event")
    }

    fun backBtnPressed(view: View) {
        startActivity(Intent(this,StartActivity::class.java))
    }
}
