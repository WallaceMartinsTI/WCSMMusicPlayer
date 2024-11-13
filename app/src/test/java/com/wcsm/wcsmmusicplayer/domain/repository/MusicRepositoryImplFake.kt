package com.wcsm.wcsmmusicplayer.domain.repository

import com.wcsm.wcsmmusicplayer.domain.model.Music
import com.wcsm.wcsmmusicplayer.util.musicsList

class MusicRepositoryImplFake : IMusicRepository {

    override suspend fun getMusicsFromData(): List<Music> {
        return musicsList
    }

}