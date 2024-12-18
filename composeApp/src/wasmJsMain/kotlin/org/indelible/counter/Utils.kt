package org.indelible.counter

import kotlinx.datetime.LocalTime



fun LocalTime.plusSecond(seconds: Int): LocalTime {
    val totalSeconds = (this.hour * 3600 + this.minute * 60 + this.second + seconds).mod(24 * 3600)

    if (totalSeconds < 0)
        return LocalTime(0, 0, 0)

    val newHour = totalSeconds / 3600
    val newMinute = (totalSeconds % 3600) / 60
    val newSecond = totalSeconds % 60

    return LocalTime(newHour, newMinute, newSecond)
}

fun LocalTime.compareToMin(): Boolean {
    return this == LocalTime(0, 0)
}

fun Int.formattedZeroTimes(): String {
    return if (this < 10) {
        "0$this"
    } else {
        this.toString()
    }
}



