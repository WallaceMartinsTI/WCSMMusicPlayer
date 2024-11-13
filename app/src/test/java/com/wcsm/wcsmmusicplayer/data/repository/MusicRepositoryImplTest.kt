package com.wcsm.wcsmmusicplayer.data.repository

import android.net.Uri
import com.google.common.truth.Truth.assertThat
import com.wcsm.wcsmmusicplayer.data.mediastore.MusicMediaStore
import com.wcsm.wcsmmusicplayer.data.mediastore.MusicMediaStoreFake
import com.wcsm.wcsmmusicplayer.data.model.MusicFromDevice
import com.wcsm.wcsmmusicplayer.domain.model.Music
import kotlinx.coroutines.test.runTest


import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MusicRepositoryImplTest {

    // MOCK
    @Mock
    private lateinit var musicMediaStoreMock: MusicMediaStore

    // FAKE
    private lateinit var musicMediaStoreFake: MusicMediaStoreFake

    private lateinit var musicRepositoryImplMock: MusicRepositoryImpl
    private lateinit var musicRepositoryImplFake: MusicRepositoryImpl

    private val uriMock1 = Mockito.mock(Uri::class.java)
    private val uriMock2 = Mockito.mock(Uri::class.java)
    private val uriMock3 = Mockito.mock(Uri::class.java)

    private val expectedMusicList = listOf(
        Music(uriMock1, "Musica 1", "Artista 1", 23948, "Album 1"),
        Music(uriMock2, "Musica 2", "Artista 2", 23741, "Album 2"),
        Music(uriMock3, "Musica 3", "Artista 3", 23371, "Album 3")
    )

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        musicRepositoryImplMock = MusicRepositoryImpl(musicMediaStoreMock)

        musicMediaStoreFake = MusicMediaStoreFake()
        musicRepositoryImplFake = MusicRepositoryImpl(musicMediaStoreFake)
    }

    // FAKE
    @Test
    fun `getMusics should return list of musics - fake`() = runTest {
        val musics = musicRepositoryImplFake.getMusicsFromData()

        assertThat(musics.size).isEqualTo(expectedMusicList.size)

        musics.forEachIndexed { index, actualMusic ->
            val expectedMusic = expectedMusicList[index]
            assertThat(actualMusic.title).isEqualTo(expectedMusic.title)
            assertThat(actualMusic.album).isEqualTo(expectedMusic.album)
            assertThat(actualMusic.duration).isEqualTo(expectedMusic.duration)
        }
    }

    // MOCK
    @Test
    fun `getMusics should return list of musics - mock`() = runTest {
        val mockMusicFromDeviceLists = listOf(
            MusicFromDevice(uriMock1, "Musica 1", "Artista 1", "storage/1", 23948, "Album 1", "Size 1", "MimeType 1", "Year 1"),
            MusicFromDevice(uriMock2, "Musica 2", "Artista 2", "storage/2", 23741, "Album 2", "Size 2", "MimeType 2", "Year 2"),
            MusicFromDevice(uriMock3, "Musica 3", "Artista 3", "storage/3", 23371, "Album 3", "Size 3", "MimeType 3", "Year 3")
        )

        Mockito.`when`(musicMediaStoreMock.fetchMusicsFromDevice()).thenReturn (mockMusicFromDeviceLists)

        val musics = musicRepositoryImplMock.getMusicsFromData()

        assertThat(musics).isEqualTo(expectedMusicList)
        Mockito.verify(musicMediaStoreMock, Mockito.times(1)).fetchMusicsFromDevice()
    }
}