package com.wcsm.wcsmmusicplayer.data.repository

import android.net.Uri
import com.google.common.truth.Truth.assertThat
import com.wcsm.wcsmmusicplayer.data.mediastore.MusicMediaStore
import com.wcsm.wcsmmusicplayer.data.mediastore.MusicMediaStoreFake
import com.wcsm.wcsmmusicplayer.data.model.MusicFromDevice
import com.wcsm.wcsmmusicplayer.domain.model.Music
import com.wcsm.wcsmmusicplayer.util.musicsFromDeviceList
import com.wcsm.wcsmmusicplayer.util.musicsList
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

    val uriMock1 = Mockito.mock(Uri::class.java)
    val uriMock2 = Mockito.mock(Uri::class.java)
    val uriMock3 = Mockito.mock(Uri::class.java)

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

        assertThat(musics.size).isEqualTo(musicsList.size)

        musics.forEachIndexed { index, actualMusic ->
            val expectedMusic = musicsList[index]
            assertThat(actualMusic.title).isEqualTo(expectedMusic.title)
            assertThat(actualMusic.album).isEqualTo(expectedMusic.album)
            assertThat(actualMusic.duration).isEqualTo(expectedMusic.duration)
        }
    }

    // MOCK
    @Test
    fun `getMusics should return list of musics - mock`() = runTest {
        Mockito.`when`(musicMediaStoreMock.fetchMusicsFromDevice()).thenReturn (musicsFromDeviceList)

        val musics = musicRepositoryImplMock.getMusicsFromData()

        assertThat(musics).isEqualTo(musicsList)
        Mockito.verify(musicMediaStoreMock, Mockito.times(1)).fetchMusicsFromDevice()
    }
}