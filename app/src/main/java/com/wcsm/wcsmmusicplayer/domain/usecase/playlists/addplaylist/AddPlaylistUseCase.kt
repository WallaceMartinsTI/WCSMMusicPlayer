package com.wcsm.wcsmmusicplayer.domain.usecase.playlists.addplaylist

import android.util.Log
import com.wcsm.wcsmmusicplayer.domain.model.Playlist
import com.wcsm.wcsmmusicplayer.domain.repository.IPlaylistRepository
import javax.inject.Inject

class AddPlaylistUseCase @Inject constructor(
    private val playlistRepository: IPlaylistRepository
) : IAddPlaylistUseCase {

    private val tag = "# AddPlaylistUseCase #"

    override suspend fun invoke(playlist: Playlist) {
        try {
            playlistRepository.saveNewPlaylist(playlist)
            Log.i(tag, "Playlist saved successfully!")
        } catch (e: Exception) {
            Log.e(tag, "Error saving playlist")
        }
    }
}