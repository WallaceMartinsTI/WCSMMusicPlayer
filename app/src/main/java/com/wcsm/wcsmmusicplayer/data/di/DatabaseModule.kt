package com.wcsm.wcsmmusicplayer.data.di

import android.content.Context
import androidx.room.Room
import com.wcsm.wcsmmusicplayer.data.dao.PlaylistDAO
import com.wcsm.wcsmmusicplayer.data.database.PlaylistDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): PlaylistDatabase {
        return Room.databaseBuilder(
            context,
            PlaylistDatabase::class.java,
            "playlist_database"
        ).build()
    }

    @Singleton
    @Provides
    fun providePlaylistDAO(database: PlaylistDatabase): PlaylistDAO {
        return database.playlistDao()
    }
}