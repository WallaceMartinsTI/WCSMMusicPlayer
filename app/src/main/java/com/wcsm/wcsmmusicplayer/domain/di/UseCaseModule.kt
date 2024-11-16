package com.wcsm.wcsmmusicplayer.domain.di

import com.wcsm.wcsmmusicplayer.domain.repository.IMusicRepository
import com.wcsm.wcsmmusicplayer.domain.repository.IPlaylistRepository
import com.wcsm.wcsmmusicplayer.domain.usecase.musics.getmusics.GetMusicsUseCase
import com.wcsm.wcsmmusicplayer.domain.usecase.musics.getmusics.IGetMusicsUseCase
import com.wcsm.wcsmmusicplayer.domain.usecase.musics.getnextmusic.GetNextMusicUseCase
import com.wcsm.wcsmmusicplayer.domain.usecase.musics.getnextmusic.IGetNextMusicUseCase
import com.wcsm.wcsmmusicplayer.domain.usecase.playlists.getplaylists.GetPlaylistsUseCase
import com.wcsm.wcsmmusicplayer.domain.usecase.playlists.getplaylists.IGetPlaylistsUseCase
import com.wcsm.wcsmmusicplayer.domain.usecase.musics.getpreviousmusic.GetPreviousMusicUseCase
import com.wcsm.wcsmmusicplayer.domain.usecase.musics.getpreviousmusic.IGetPreviousMusicUseCase
import com.wcsm.wcsmmusicplayer.domain.usecase.playlists.addplaylist.AddPlaylistUseCase
import com.wcsm.wcsmmusicplayer.domain.usecase.playlists.addplaylist.IAddPlaylistUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetMusicsUseCase(musicRepository: IMusicRepository) : IGetMusicsUseCase {
        return GetMusicsUseCase(musicRepository)
    }

    @Provides
    fun provideGetNextMusicUseCase() : IGetNextMusicUseCase {
        return GetNextMusicUseCase()
    }

    @Provides
    fun provideGetPreviousMusicUseCase() : IGetPreviousMusicUseCase {
        return GetPreviousMusicUseCase()
    }

    @Provides
    fun provideGetPlaylistsUseCase(playlistsRepository: IPlaylistRepository) : IGetPlaylistsUseCase {
        return GetPlaylistsUseCase(playlistsRepository)
    }

    @Provides
    fun provideAddPlaylistUseCase(playlistsRepository: IPlaylistRepository) : IAddPlaylistUseCase {
        return AddPlaylistUseCase(playlistsRepository)
    }

}