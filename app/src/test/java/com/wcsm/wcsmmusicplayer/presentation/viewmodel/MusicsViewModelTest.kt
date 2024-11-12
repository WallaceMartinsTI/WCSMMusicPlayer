package com.wcsm.wcsmmusicplayer.presentation.viewmodel

import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.wcsm.wcsmmusicplayer.domain.model.Music
import com.wcsm.wcsmmusicplayer.domain.usecase.GetMusicsUseCase
import com.wcsm.wcsmmusicplayer.domain.usecase.GetMusicsUseCaseFake
import com.wcsm.wcsmmusicplayer.util.getOrAwaitValue
import kotlinx.coroutines.test.runTest

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MusicsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // Mock
    @Mock
    private lateinit var getMusicsUseCaseMock: GetMusicsUseCase
    private lateinit var musicsViewModelMock: MusicsViewModel

    // Fake
    private lateinit var getMusicsUseCaseFake: GetMusicsUseCaseFake
    private lateinit var musicsViewModelFake: MusicsViewModel

    private val uriMock1 = Mockito.mock(Uri::class.java)
    private val uriMock2 = Mockito.mock(Uri::class.java)
    private val uriMock3 = Mockito.mock(Uri::class.java)

    @Before
    fun setUp() {
        // Mock
        MockitoAnnotations.openMocks(this)
        musicsViewModelMock = MusicsViewModel(getMusicsUseCaseMock)

        // Fake
        getMusicsUseCaseFake = GetMusicsUseCaseFake()
        musicsViewModelFake = MusicsViewModel(getMusicsUseCaseFake)
    }

    // Fake
    @Test
    fun `getMusics should fill musics live data value with the musics list - fake`() = runTest {
        musicsViewModelFake.getMusics()
        val liveData = musicsViewModelFake.musics.getOrAwaitValue()

        assertThat(liveData).isNotEmpty()
    }

    // Mock
    @Test
    fun `getMusics should fill musics live data value with the musics list - mock`() = runTest {
        Mockito.`when`(getMusicsUseCaseMock()).thenReturn(
            listOf(
                Music(uriMock1, "Musica 1", "Artista 1", 23948, "Album 1"),
                Music(uriMock2, "Musica 2", "Artista 2", 23741, "Album 2"),
                Music(uriMock3, "Musica 3", "Artista 3", 23371, "Album 3")
            )
        )

        musicsViewModelMock.getMusics()
        val liveData = musicsViewModelMock.musics.getOrAwaitValue()

        assertThat(liveData).isNotEmpty()
    }
}