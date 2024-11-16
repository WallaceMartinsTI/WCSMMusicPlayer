package com.wcsm.wcsmmusicplayer.domain.usecase.playlists.getplaylists

import com.wcsm.wcsmmusicplayer.domain.model.Playlist

interface IGetPlaylistsUseCase {

    suspend operator fun invoke() : List<Playlist>

}