package com.wcsm.wcsmmusicplayer.domain.usecase.playlists.deleteplaylist

import com.wcsm.wcsmmusicplayer.domain.model.Playlist
import com.wcsm.wcsmmusicplayer.domain.usecase.PlaylistResult

interface IDeletePlaylistUseCase {

    suspend operator fun invoke(playlist: Playlist) : PlaylistResult

}