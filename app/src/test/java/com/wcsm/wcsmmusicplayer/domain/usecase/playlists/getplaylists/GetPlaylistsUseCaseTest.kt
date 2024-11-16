package com.wcsm.wcsmmusicplayer.domain.usecase.playlists.getplaylists

import com.google.common.truth.Truth.assertThat
import com.wcsm.wcsmmusicplayer.domain.repository.IPlaylistRepository
import com.wcsm.wcsmmusicplayer.domain.repository.PlaylistRepositoryFake
import com.wcsm.wcsmmusicplayer.util.emptyPlaylistsList
import com.wcsm.wcsmmusicplayer.util.playlistsWithSongsList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetPlaylistsUseCaseTest {

    // Mock
    @Mock
    private lateinit var playlistRepositoryMock: IPlaylistRepository
    private lateinit var getPlaylistsUseCaseMock: IGetPlaylistsUseCase

    // Fake
    private lateinit var playlistRepositoryFake: IPlaylistRepository
    private lateinit var getPlaylistsUseCaseFake: IGetPlaylistsUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getPlaylistsUseCaseMock = GetPlaylistsUseCase(playlistRepositoryMock)

        playlistRepositoryFake = PlaylistRepositoryFake()
        getPlaylistsUseCaseFake = GetPlaylistsUseCase(playlistRepositoryFake)
    }

    // Fake
    @Test
    fun `validate invoke should return playlists with musics - fake`() = runTest {
        val playlists = getPlaylistsUseCaseFake()
        assertThat(playlists).isNotEmpty()
        assertThat(playlists.size).isEqualTo(playlistsWithSongsList.size)
    }

    // Mock
    @Test
    fun `invoke should return an empty list - mock`() = runTest {
        Mockito.`when`(playlistRepositoryMock.getAllPlaylistsFromData()).thenReturn(emptyList())
        val emptyPlaylists = getPlaylistsUseCaseMock()
        assertThat(emptyPlaylists).isEmpty()
    }

    @Test
    fun `validate invoke should return playlists with no musics - mock`() = runTest {
        Mockito.`when`(playlistRepositoryMock.getAllPlaylistsFromData()).thenReturn(
            emptyPlaylistsList
        )
        val playlists = getPlaylistsUseCaseMock()
        assertThat(playlists).isNotEmpty()
        assertThat(playlists.size).isEqualTo(emptyPlaylistsList.size)
    }

    @Test
    fun `validate invoke should return playlists with musics - mock`() = runTest {
        Mockito.`when`(playlistRepositoryMock.getAllPlaylistsFromData()).thenReturn(
            playlistsWithSongsList
        )
        val playlists = getPlaylistsUseCaseMock()
        assertThat(playlists).isNotEmpty()
        assertThat(playlists.size).isEqualTo(playlistsWithSongsList.size)
    }
}