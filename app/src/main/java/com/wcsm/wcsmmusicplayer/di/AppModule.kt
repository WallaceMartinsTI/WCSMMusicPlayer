package com.wcsm.wcsmmusicplayer.di

import android.content.Context
import com.wcsm.wcsmmusicplayer.data.mediastore.MusicMediaStore
import com.wcsm.wcsmmusicplayer.data.repository.MusicRepositoryImpl
import com.wcsm.wcsmmusicplayer.domain.repository.IMusicRepository
import com.wcsm.wcsmmusicplayer.domain.usecase.GetMusicsUseCase
import com.wcsm.wcsmmusicplayer.domain.usecase.IGetMusicsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideMediaStore(@ApplicationContext context: Context) : MusicMediaStore {
        return MusicMediaStore(context)
    }

    @Provides
    fun providesMusicRepository(musicMediaStore: MusicMediaStore) : IMusicRepository {
        return MusicRepositoryImpl(musicMediaStore)
    }

    @Provides
    fun provideGetMusicsUseCase(musicRepository: IMusicRepository) : IGetMusicsUseCase {
        return GetMusicsUseCase(musicRepository)
    }

}