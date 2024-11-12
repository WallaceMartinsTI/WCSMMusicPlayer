package com.wcsm.wcsmmusicplayer.domain.usecase

import android.net.Uri
import com.google.common.truth.Truth.assertThat
import com.wcsm.wcsmmusicplayer.data.repository.MusicRepositoryImpl
import com.wcsm.wcsmmusicplayer.domain.model.Music
import com.wcsm.wcsmmusicplayer.domain.repository.MusicRepositoryImplFake
import kotlinx.coroutines.test.runTest

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetMusicsUseCaseTest {

    // Mock
    @Mock
    private lateinit var musicRepositoryImplMock: MusicRepositoryImpl
    private lateinit var getMusicsUseCaseMock: GetMusicsUseCase

    // Fake
    private lateinit var musicRepositoryImplFake: MusicRepositoryImplFake
    private lateinit var getMusicsUseCaseFake: GetMusicsUseCase

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
        // Mock
        MockitoAnnotations.openMocks(this)
        getMusicsUseCaseMock = GetMusicsUseCase(musicRepositoryImplMock)

        // Fake
        musicRepositoryImplFake = MusicRepositoryImplFake()
        getMusicsUseCaseFake = GetMusicsUseCase(musicRepositoryImplFake)
    }

    // Fake
    @Test
    fun `invoke should get music from data layer and return the musics list - fake`() = runTest {
        val musics = getMusicsUseCaseFake()

        assertThat(musics.size).isEqualTo(expectedMusicList.size)

        musics.forEachIndexed { index, actualMusic ->
            val expectedMusic = expectedMusicList[index]
            assertThat(actualMusic.title).isEqualTo(expectedMusic.title)
            assertThat(actualMusic.album).isEqualTo(expectedMusic.album)
            assertThat(actualMusic.duration).isEqualTo(expectedMusic.duration)
        }
    }

    // Mock
    @Test
    fun `invoke should get music from data layer and return the musics list - mock`() = runTest {
        val mockMusicFromData = listOf(
            Music(uriMock1, "Musica 1", "Artista 1", 23948, "Album 1"),
            Music(uriMock2, "Musica 2", "Artista 2", 23741, "Album 2"),
            Music(uriMock3, "Musica 3", "Artista 3", 23371, "Album 3")
        )

        Mockito.`when`(musicRepositoryImplMock.getMusicsFromData()).thenReturn(mockMusicFromData)

        val musics = getMusicsUseCaseMock()
        assertThat(musics).isEqualTo(expectedMusicList)
    }

}