package com.wcsm.wcsmmusicplayer.util

import android.net.Uri
import com.wcsm.wcsmmusicplayer.data.model.MusicFromDevice
import com.wcsm.wcsmmusicplayer.domain.model.Music
import com.wcsm.wcsmmusicplayer.domain.model.Playlist
import org.mockito.Mockito

val uriMock1: Uri = Mockito.mock(Uri::class.java)
val uriMock2: Uri  = Mockito.mock(Uri::class.java)
val uriMock3: Uri  = Mockito.mock(Uri::class.java)
val uriMock4: Uri  = Mockito.mock(Uri::class.java)
val uriMock5: Uri  = Mockito.mock(Uri::class.java)
val uriMock6: Uri  = Mockito.mock(Uri::class.java)

val musicsList = listOf(
    Music(uriMock1, "Musica 1", "Artista 1", 23948, "Album 1", emptyList()),
    Music(uriMock2, "Musica 2", "Artista 2", 23741, "Album 2", emptyList()),
    Music(uriMock3, "Musica 3", "Artista 3", 23371, "Album 3", emptyList()),
    Music(uriMock4, "Musica 4", "Artista 4", 23371, "Album 4", emptyList()),
    Music(uriMock5, "Musica 5", "Artista 5", 23371, "Album 5", emptyList()),
    Music(uriMock6, "Musica 6", "Artista 6", 23371, "Album 6", emptyList())
)

val musicsFromDeviceList = listOf(
    MusicFromDevice(uriMock1, "Musica 1", "Artista 1", "storage/1", 23948, "Album 1", "Size 1", "MimeType 1", "Year 1"),
    MusicFromDevice(uriMock2, "Musica 2", "Artista 2", "storage/2", 23741, "Album 2", "Size 2", "MimeType 2", "Year 2"),
    MusicFromDevice(uriMock3, "Musica 3", "Artista 3", "storage/3", 23371, "Album 3", "Size 3", "MimeType 3", "Year 3"),
    MusicFromDevice(uriMock4, "Musica 4", "Artista 4", "storage/4", 23371, "Album 4", "Size 4", "MimeType 4", "Year 4"),
    MusicFromDevice(uriMock5, "Musica 5", "Artista 5", "storage/5", 23371, "Album 5", "Size 5", "MimeType 5", "Year 5"),
    MusicFromDevice(uriMock6, "Musica 6", "Artista 6", "storage/6", 23371, "Album 6", "Size 6", "MimeType 6", "Year 6")
)


val emptyPlaylistsList = listOf(
    Playlist(0, "Rock", emptyList()),
    Playlist(1, "Eletrônica", emptyList()),
    Playlist(2, "Forró", emptyList()),
    Playlist(3, "Pop", emptyList()),
    Playlist(4, "Sertanejo", emptyList()),
    Playlist(5, "Rap", emptyList()),
)

val playlistsWithSongsList = listOf(
    Playlist(0, "Rock", listOf(musicsList[0], musicsList[1], musicsList[2])),
    Playlist(1, "Eletrônica", listOf(musicsList[0], musicsList[1], musicsList[2])),
    Playlist(2, "Forró", listOf(musicsList[0], musicsList[1], musicsList[2])),
    Playlist(3, "Pop", listOf(musicsList[0], musicsList[1], musicsList[2])),
    Playlist(4, "Sertanejo", listOf(musicsList[0], musicsList[1], musicsList[2])),
    Playlist(5, "Rap", listOf(musicsList[0], musicsList[1], musicsList[2])),
)
