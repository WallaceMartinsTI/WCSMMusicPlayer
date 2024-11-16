package com.wcsm.wcsmmusicplayer.presentation.viewmodel

import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.wcsm.wcsmmusicplayer.domain.model.Music
import com.wcsm.wcsmmusicplayer.domain.usecase.musics.getmusics.GetMusicsUseCase
import com.wcsm.wcsmmusicplayer.domain.usecase.musics.getmusics.GetMusicsUseCaseFake
import com.wcsm.wcsmmusicplayer.domain.usecase.musics.getnextmusic.GetNextMusicUseCase
import com.wcsm.wcsmmusicplayer.domain.usecase.musics.getnextmusic.GetNextMusicUseCaseFake
import com.wcsm.wcsmmusicplayer.domain.usecase.musics.getpreviousmusic.GetPreviousMusicUseCase
import com.wcsm.wcsmmusicplayer.domain.usecase.musics.getpreviousmusic.GetPreviousMusicUseCaseFake
import com.wcsm.wcsmmusicplayer.util.getOrAwaitValue
import com.wcsm.wcsmmusicplayer.util.musicsList
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
    @Mock
    private lateinit var getNextMusicUseCaseMock: GetNextMusicUseCase
    @Mock
    private lateinit var getPreviousMusicUseCaseMock: GetPreviousMusicUseCase
    private lateinit var musicsViewModelMock: MusicsViewModel

    // Fake
    private lateinit var getMusicsUseCaseFake: GetMusicsUseCaseFake
    private lateinit var getNextMusicUseCaseFake: GetNextMusicUseCaseFake
    private lateinit var getPreviousMusicUseCaseFake: GetPreviousMusicUseCaseFake

    private lateinit var musicsViewModelFake: MusicsViewModel

    @Before
    fun setUp() {
        // Mock
        MockitoAnnotations.openMocks(this)
        musicsViewModelMock = MusicsViewModel(
            getMusicsUseCaseMock,
            getNextMusicUseCaseMock,
            getPreviousMusicUseCaseMock
        )

        // Fake
        getMusicsUseCaseFake = GetMusicsUseCaseFake()
        getNextMusicUseCaseFake = GetNextMusicUseCaseFake()
        getPreviousMusicUseCaseFake = GetPreviousMusicUseCaseFake()

        musicsViewModelFake = MusicsViewModel(
            getMusicsUseCaseFake,
            getNextMusicUseCaseFake,
            getPreviousMusicUseCaseFake
        )
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
        Mockito.`when`(getMusicsUseCaseMock()).thenReturn(musicsList)

        musicsViewModelMock.getMusics()
        val liveData = musicsViewModelMock.musics.getOrAwaitValue()

        assertThat(liveData).isNotEmpty()
    }
}