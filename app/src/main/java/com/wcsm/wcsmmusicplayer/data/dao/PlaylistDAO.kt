package com.wcsm.wcsmmusicplayer.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wcsm.wcsmmusicplayer.domain.model.Playlist

@Dao
interface PlaylistDAO {

    @Query("SELECT * FROM playlist")
    suspend fun getAllPlaylistsFromDB(): List<Playlist>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPlaylistToDB(playlist: Playlist)

    @Delete
    suspend fun deletePlaylistInDB(playlist: Playlist)

}