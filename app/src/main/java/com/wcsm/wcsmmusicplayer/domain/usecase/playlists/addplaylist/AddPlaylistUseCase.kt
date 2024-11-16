package com.wcsm.wcsmmusicplayer.domain.usecase.playlists.addplaylist

import android.util.Log
import com.wcsm.wcsmmusicplayer.domain.model.Playlist
import com.wcsm.wcsmmusicplayer.domain.repository.IPlaylistRepository
import com.wcsm.wcsmmusicplayer.domain.usecase.PlaylistResult
import javax.inject.Inject

class AddPlaylistUseCase @Inject constructor(
    private val playlistRepository: IPlaylistRepository
) : IAddPlaylistUseCase {

    private val tag = "# AddPlaylistUseCase #"

    override suspend fun invoke(playlist: Playlist) : PlaylistResult {
        val validationResult = validatePlaylistTitle(playlist.title)

        if(validationResult is PlaylistResult.Error) {
            return validationResult
        }

        if(playlist.musics.isEmpty()) {
            return PlaylistResult.Error(
                message = "Você não pode criar uma playlist vazia.",
                type = PlaylistResult.ErrorType.EMPTY_PLAYLIST
            )
        }

        return try {
            playlistRepository.saveNewPlaylist(playlist)
            Log.i(tag, "Playlist saved successfully!")
            PlaylistResult.Success("Playlist criada com sucesso!")
        } catch (e: Exception) {
            Log.e(tag, "Error saving playlist")
            PlaylistResult.Error(
                message = "Erro inesperado ao salvar a playlist.",
                type = PlaylistResult.ErrorType.UNKNOWN
            )
        }
    }

    private fun validatePlaylistTitle(playlistTitle: String): PlaylistResult {
        return when {
            playlistTitle.isEmpty() -> PlaylistResult.Error(
                message = "O título da playlist não pode ser vazio.",
                type = PlaylistResult.ErrorType.INVALID_TITLE)
            playlistTitle.length < 3 -> PlaylistResult.Error(
                message = "Título muito curto (mínimo de 3 caracteres).",
                type = PlaylistResult.ErrorType.INVALID_TITLE)
            else -> PlaylistResult.Success("")
        }
    }
}