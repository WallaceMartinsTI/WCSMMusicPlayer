package com.wcsm.wcsmmusicplayer.domain.usecase.getmusics

import android.net.Uri
import com.google.common.truth.Truth.assertThat
import com.wcsm.wcsmmusicplayer.data.repository.MusicRepositoryImpl
import com.wcsm.wcsmmusicplayer.domain.model.Music
import com.wcsm.wcsmmusicplayer.domain.repository.MusicRepositoryImplFake
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
class GetMusicsUseCaseTest {

    // Mock
    @Mock
    private lateinit var musicRepositoryImplMock: MusicRepositoryImpl
    private lateinit var getMusicsUseCaseMock: GetMusicsUseCase

    // Fake
    private lateinit var musicRepositoryImplFake: MusicRepositoryImplFake
    private lateinit var getMusicsUseCaseFake: GetMusicsUseCase

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

        assertThat(musics.size).isEqualTo(musicsList.size)

        musics.forEachIndexed { index, actualMusic ->
            val expectedMusic = musicsList[index]
            assertThat(actualMusic.title).isEqualTo(expectedMusic.title)
            assertThat(actualMusic.album).isEqualTo(expectedMusic.album)
            assertThat(actualMusic.duration).isEqualTo(expectedMusic.duration)
        }
    }

    // Mock
    @Test
    fun `invoke should get music from data layer and return the musics list - mock`() = runTest {
        Mockito.`when`(musicRepositoryImplMock.getMusicsFromData()).thenReturn(musicsList)

        val musics = getMusicsUseCaseMock()
        assertThat(musics).isEqualTo(musicsList)
    }

}