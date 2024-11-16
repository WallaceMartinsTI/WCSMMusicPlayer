package com.wcsm.wcsmmusicplayer.domain.repository

import com.wcsm.wcsmmusicplayer.domain.model.Playlist
import com.wcsm.wcsmmusicplayer.util.playlistsWithSongsList

class PlaylistRepositoryFake : IPlaylistRepository {
    override suspend fun getAllPlaylistsFromData(): List<Playlist> {
        return playlistsWithSongsList
    }

    override suspend fun saveNewPlaylist(playlist: Playlist) {
        TODO("Not yet implemented")
    }

    override suspend fun deletePlaylist(playlist: Playlist) {
        TODO("Not yet implemented")
    }
}