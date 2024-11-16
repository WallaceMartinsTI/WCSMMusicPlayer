package com.wcsm.wcsmmusicplayer.domain.usecase.playlists.getplaylists

import android.util.Log
import com.wcsm.wcsmmusicplayer.domain.model.Playlist
import com.wcsm.wcsmmusicplayer.domain.repository.IPlaylistRepository

class GetPlaylistsUseCase(
    private val playlistRepository: IPlaylistRepository
) : IGetPlaylistsUseCase {

    private val tag = "# GetPlaylistsUseCase #"

    override suspend fun invoke(): List<Playlist> {
        try {
            val playlists = playlistRepository.getAllPlaylistsFromData()
            Log.i(tag, "Get playlists from data successfully!")
            return playlists
        } catch (e: Exception) {
            Log.e(tag, "Error getting playlists from data", e)
            return emptyList()
        }
    }
}