package com.wcsm.wcsmmusicplayer.di

import com.wcsm.wcsmmusicplayer.domain.repository.IMusicRepository
import com.wcsm.wcsmmusicplayer.domain.usecase.getmusics.GetMusicsUseCase
import com.wcsm.wcsmmusicplayer.domain.usecase.getmusics.IGetMusicsUseCase
import com.wcsm.wcsmmusicplayer.domain.usecase.getnextmusic.GetNextMusicUseCase
import com.wcsm.wcsmmusicplayer.domain.usecase.getnextmusic.IGetNextMusicUseCase
import com.wcsm.wcsmmusicplayer.domain.usecase.getpreviousmusic.GetPreviousMusicUseCase
import com.wcsm.wcsmmusicplayer.domain.usecase.getpreviousmusic.IGetPreviousMusicUseCase
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

}