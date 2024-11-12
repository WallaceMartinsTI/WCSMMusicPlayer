package com.wcsm.wcsmmusicplayer.domain.repository

import com.wcsm.wcsmmusicplayer.domain.model.Music

interface IMusicRepository {

    suspend fun getMusicsFromData() : List<Music>

}