package com.wcsm.wcsmmusicplayer.domain.model

import android.net.Uri

data class Music(
    val uri: String,
    val title: String,
    val artist: String,
    val duration: Int,
    val album: String,
    val playlists: List<String>,
    var isCheckedToAddToPlaylist: Boolean = false
)
