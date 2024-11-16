package com.wcsm.wcsmmusicplayer.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.wcsm.wcsmmusicplayer.data.dao.PlaylistDAO
import com.wcsm.wcsmmusicplayer.data.util.Converters
import com.wcsm.wcsmmusicplayer.domain.model.Playlist

@TypeConverters(Converters::class)
@Database(entities = [Playlist::class], version = 1, exportSchema = false)
abstract class PlaylistDatabase : RoomDatabase() {
    abstract fun playlistDao(): PlaylistDAO
}