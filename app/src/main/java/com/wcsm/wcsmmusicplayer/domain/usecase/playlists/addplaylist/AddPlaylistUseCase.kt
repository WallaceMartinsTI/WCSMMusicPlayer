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
        var isUpdate = false

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
            val existingPlaylist = playlistRepository.getAllPlaylistsFromData()
                .find { it.uid == playlist.uid }

            val playlistToSave = if(existingPlaylist != null) {
                isUpdate = true
                playlist.copy(uid = existingPlaylist.uid)
            } else {
                playlist
            }
            playlistRepository.saveNewPlaylist(playlistToSave)
            val message = if(isUpdate) "Playlist atualizada com sucesso!" else "Playlist criada com sucesso!"
            Log.i(tag, "message: $message")
            PlaylistResult.Success(message)
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