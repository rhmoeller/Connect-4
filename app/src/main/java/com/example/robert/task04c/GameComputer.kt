package com.example.robert.task04c

import java.security.SecureRandom
import java.util.*

class GameComputer {
    private val random = SecureRandom()

    fun pickColumn(mData: Array<IntArray>): Int {
        var choices = arrayListOf<Int>()
        fun IntArray.hasSpace(): Boolean {
            var items = 0
            this.forEach { item -> if (item > 0) ++items }
            return this.size != items
        }

        for (index in 0 until mData.size)
            if (mData[index].hasSpace())
                choices.add(index)

        return choices[random.nextInt(choices.size)]
    }
}