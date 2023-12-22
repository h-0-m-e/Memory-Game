package ru.h0me.memorygame.util

object TimeToStringCounter {

    fun count (timeInSec: Long): String{
        if(timeInSec == 0L) return "00:00"
        var minutes = (timeInSec / 60).toString()
        var seconds = (timeInSec % 60).toString()

        if(minutes.toInt() < 10) {
            minutes = "0$minutes"
        }
        if(seconds.toInt() < 10) {
            seconds = "0$seconds"
        }

        return "$minutes:$seconds"
    }
}
