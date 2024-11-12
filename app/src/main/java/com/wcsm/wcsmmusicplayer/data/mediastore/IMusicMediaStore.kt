package com.wcsm.wcsmmusicplayer.data.mediastore

import com.wcsm.wcsmmusicplayer.data.model.MusicFromDevice

interface IMusicMediaStore {

    fun fetchMusicsFromDevice(): List<MusicFromDevice>

}