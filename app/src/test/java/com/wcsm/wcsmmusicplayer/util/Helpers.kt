package com.wcsm.wcsmmusicplayer.util

import android.net.Uri
import com.wcsm.wcsmmusicplayer.domain.model.Music
import org.mockito.Mockito

val uriMock1 = Mockito.mock(Uri::class.java)
val uriMock2 = Mockito.mock(Uri::class.java)
val uriMock3 = Mockito.mock(Uri::class.java)
val uriMock4 = Mockito.mock(Uri::class.java)
val uriMock5 = Mockito.mock(Uri::class.java)
val uriMock6 = Mockito.mock(Uri::class.java)

val musicsList = listOf(
    Music(uriMock1, "Musica 1", "Artista 1", 23948, "Album 1"),
    Music(uriMock2, "Musica 2", "Artista 2", 23741, "Album 2"),
    Music(uriMock3, "Musica 3", "Artista 3", 23371, "Album 3"),
    Music(uriMock4, "Musica 4", "Artista 4", 23371, "Album 4"),
    Music(uriMock5, "Musica 5", "Artista 5", 23371, "Album 5"),
    Music(uriMock6, "Musica 6", "Artista 6", 23371, "Album 6")
)