package com.wcsm.wcsmmusicplayer.data.repository

import com.google.common.truth.Truth.assertThat
import com.wcsm.wcsmmusicplayer.data.dao.PlaylistDAO
import com.wcsm.wcsmmusicplayer.domain.repository.IPlaylistRepository
import com.wcsm.wcsmmusicplayer.util.emptyPlaylistsList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PlaylistRepositoryImplTest {

    @Mock
    private lateinit var playlistDaoMock: PlaylistDAO
    private lateinit var playlistsRepositoryMock: IPlaylistRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        playlistsRepositoryMock = PlaylistRepositoryImpl(playlistDaoMock)
    }

    @Test
    fun `getPlaylists should return a empty list of playlists`() = runTest {
        Mockito.`when`(playlistDaoMock.getAllPlaylistsFromDB()).thenReturn(emptyList())
        val playlists = playlistsRepositoryMock.getAllPlaylistsFromData()
        assertThat(playlists).isEmpty()
        Mockito.verify(playlistDaoMock, Mockito.times(1)).getAllPlaylistsFromDB()
    }

    @Test
    fun `getPlaylists should return a list of playlists with no musics`() = runTest {
        Mockito.`when`(playlistDaoMock.getAllPlaylistsFromDB()).thenReturn(emptyPlaylistsList)
        val playlists = playlistsRepositoryMock.getAllPlaylistsFromData()

        assertThat(playlists.size).isEqualTo(6)
        Mockito.verify(playlistDaoMock, Mockito.times(1)).getAllPlaylistsFromDB()

        playlists.forEach { playlist ->
            assertThat(playlist.musics).isEmpty()
        }
    }
}