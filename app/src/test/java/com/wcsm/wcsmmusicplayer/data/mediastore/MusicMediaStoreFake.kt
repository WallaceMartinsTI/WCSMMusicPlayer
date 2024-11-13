package com.wcsm.wcsmmusicplayer.data.mediastore

import android.net.Uri
import com.wcsm.wcsmmusicplayer.data.model.MusicFromDevice
import org.mockito.Mockito

class MusicMediaStoreFake : IMusicMediaStore {

    override fun fetchMusicsFromDevice(): List<MusicFromDevice> {
        val uriMock1 = Mockito.mock(Uri::class.java)
        val uriMock2 = Mockito.mock(Uri::class.java)
        val uriMock3 = Mockito.mock(Uri::class.java)

        return listOf(
            MusicFromDevice(uriMock1, "Musica 1", "Artista 1", "storage/1", 23948, "Album 1", "Size 1", "MimeType 1", "Year 1"),
            MusicFromDevice(uriMock2, "Musica 2", "Artista 2", "storage/2", 23741, "Album 2", "Size 2", "MimeType 2", "Year 2"),
            MusicFromDevice(uriMock3, "Musica 3", "Artista 3", "storage/3", 23371, "Album 3", "Size 3", "MimeType 3", "Year 3")
        )
    }

}