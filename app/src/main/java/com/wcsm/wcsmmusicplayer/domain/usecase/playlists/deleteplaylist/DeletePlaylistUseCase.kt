package com.wcsm.wcsmmusicplayer.domain.usecase.playlists.deleteplaylist

import android.util.Log
import com.wcsm.wcsmmusicplayer.domain.model.Playlist
import com.wcsm.wcsmmusicplayer.domain.repository.IPlaylistRepository
import com.wcsm.wcsmmusicplayer.domain.usecase.PlaylistResult
import javax.inject.Inject

class DeletePlaylistUseCase @Inject constructor(
    private val playlistRepository: IPlaylistRepository
) : IDeletePlaylistUseCase {

    private val tag = "# DeletePlaylistUseCase #"

    override suspend fun invoke(playlist: Playlist): PlaylistResult {
        return try {
            playlistRepository.deletePlaylist(playlist)
            Log.i(tag, "Playlist deleted successfully!")
            PlaylistResult.Success("Playlist deletada com sucesso!", crudAction = PlaylistResult.CrudAction.DELETE_PLAYLIST)
        } catch (e: Exception) {
            Log.e(tag, "Error deleting playlist")
            PlaylistResult.Error(
                message = "Erro inesperado ao deletar a playlist.",
                crudAction = PlaylistResult.CrudAction.DELETE_PLAYLIST,
                type = PlaylistResult.ErrorType.UNKNOWN
            )
        }
    }

}