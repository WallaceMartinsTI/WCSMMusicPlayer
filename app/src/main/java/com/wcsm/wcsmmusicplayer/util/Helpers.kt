package com.wcsm.wcsmmusicplayer.util

import java.util.Locale

fun formatDurationIntToString(durationInMillis: Int) : String {
    val totalSeconds = durationInMillis / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format(Locale("pt", "BR"),"%02d:%02d", minutes, seconds)
}