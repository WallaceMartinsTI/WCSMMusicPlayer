package com.wcsm.wcsmmusicplayer.domain.repository

import com.wcsm.wcsmmusicplayer.domain.model.Playlist

interface IPlaylistRepository {

    suspend fun getAllPlaylistsFromData() : List<Playlist>

    suspend fun saveNewPlaylist(playlist: Playlist)

    suspend fun deletePlaylist(playlist: Playlist)

}