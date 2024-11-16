package com.wcsm.wcsmmusicplayer.domain.usecase.playlists.addplaylist

import com.wcsm.wcsmmusicplayer.domain.model.Playlist

interface IAddPlaylistUseCase {

    suspend operator fun invoke(playlist: Playlist)

}