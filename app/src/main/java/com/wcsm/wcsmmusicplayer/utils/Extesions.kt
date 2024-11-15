package com.wcsm.wcsmmusicplayer.utils

import com.wcsm.wcsmmusicplayer.data.model.MusicFromDevice
import com.wcsm.wcsmmusicplayer.domain.model.Music

fun MusicFromDevice.toMusic() : Music {
    return Music(
        uri = this.uri,
        title = this.title,
        artist = this.artist,
        duration = this.duration,
        album = this.album,
        playlists = emptyList()
    )
}