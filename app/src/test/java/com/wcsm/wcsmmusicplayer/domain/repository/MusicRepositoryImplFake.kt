package com.wcsm.wcsmmusicplayer.domain.repository

import android.net.Uri
import com.wcsm.wcsmmusicplayer.domain.model.Music
import org.mockito.Mockito

class MusicRepositoryImplFake : IMusicRepository {

    override suspend fun getMusicsFromData(): List<Music> {
        val uriMock1 = Mockito.mock(Uri::class.java)
        val uriMock2 = Mockito.mock(Uri::class.java)
        val uriMock3 = Mockito.mock(Uri::class.java)

        return listOf(
            Music(uriMock1, "Musica 1", "Artista 1", 23948, "Album 1"),
            Music(uriMock2, "Musica 2", "Artista 2", 23741, "Album 2"),
            Music(uriMock3, "Musica 3", "Artista 3", 23371, "Album 3")
        )
    }

}