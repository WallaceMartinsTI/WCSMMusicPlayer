package com.wcsm.wcsmmusicplayer.data.model

import android.net.Uri

data class MusicFromDevice(
    val uri: Uri,
    val title: String,
    val artist: String,
    val data: String,
    val duration: Int,
    val album: String,
    val size: String,
    val mimeType: String,
    val year: String
)