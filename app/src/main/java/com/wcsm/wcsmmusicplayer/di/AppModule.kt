package com.wcsm.wcsmmusicplayer.di

import android.content.Context
import com.wcsm.wcsmmusicplayer.data.dao.PlaylistDAO
import com.wcsm.wcsmmusicplayer.data.mediastore.IMusicMediaStore
import com.wcsm.wcsmmusicplayer.data.mediastore.MusicMediaStore
import com.wcsm.wcsmmusicplayer.data.repository.MusicRepositoryImpl
import com.wcsm.wcsmmusicplayer.data.repository.PlaylistRepositoryImpl
import com.wcsm.wcsmmusicplayer.domain.repository.IMusicRepository
import com.wcsm.wcsmmusicplayer.domain.repository.IPlaylistRepository
import com.wcsm.wcsmmusicplayer.domain.usecase.musics.getmusics.GetMusicsUseCase
import com.wcsm.wcsmmusicplayer.domain.usecase.musics.getmusics.IGetMusicsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideMediaStore(@ApplicationContext context: Context) : IMusicMediaStore {
        return MusicMediaStore(context)
    }

    @Provides
    fun providesMusicRepository(musicMediaStore: IMusicMediaStore) : IMusicRepository {
        return MusicRepositoryImpl(musicMediaStore)
    }

    @Provides
    fun providePlaylistRepository(playlistDAO: PlaylistDAO) : IPlaylistRepository {
        return PlaylistRepositoryImpl(playlistDAO)
    }

}