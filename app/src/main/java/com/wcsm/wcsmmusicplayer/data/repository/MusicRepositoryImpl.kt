package com.wcsm.wcsmmusicplayer.data.repository

import com.wcsm.wcsmmusicplayer.data.mediastore.IMusicMediaStore
import com.wcsm.wcsmmusicplayer.domain.model.Music
import com.wcsm.wcsmmusicplayer.domain.repository.IMusicRepository
import com.wcsm.wcsmmusicplayer.data.util.toMusic
import javax.inject.Inject

class MusicRepositoryImpl @Inject constructor(
    private val musicMediaStore: IMusicMediaStore
) : IMusicRepository {
    override suspend fun getMusicsFromData(): List<Music> {
        val musics = musicMediaStore.fetchMusicsFromDevice()
        return musics.map { it.toMusic() }
    }
}