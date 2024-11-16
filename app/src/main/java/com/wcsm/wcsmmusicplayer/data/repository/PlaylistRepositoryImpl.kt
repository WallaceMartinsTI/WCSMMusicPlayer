package com.wcsm.wcsmmusicplayer.data.repository

import com.wcsm.wcsmmusicplayer.data.dao.PlaylistDAO
import com.wcsm.wcsmmusicplayer.domain.model.Playlist
import com.wcsm.wcsmmusicplayer.domain.repository.IPlaylistRepository
import javax.inject.Inject

class PlaylistRepositoryImpl @Inject constructor(
    private val playlistDAO: PlaylistDAO
) : IPlaylistRepository {
    override suspend fun getAllPlaylistsFromData(): List<Playlist> {
        return playlistDAO.getAllPlaylistsFromDB()
    }

    override suspend fun saveNewPlaylist(playlist: Playlist) {
        return playlistDAO.addPlaylistToDB(playlist)
    }

    override suspend fun deletePlaylist(playlist: Playlist) {
        return playlistDAO.deletePlaylistInDB(playlist)
    }
}