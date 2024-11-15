package com.wcsm.wcsmmusicplayer.domain.model

import android.net.Uri

data class Music(
    val uri: Uri,
    val title: String,
    val artist: String,
    val duration: Int,
    val album: String,
    val playlists: List<String>
)
