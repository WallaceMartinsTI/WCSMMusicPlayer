package com.wcsm.wcsmmusicplayer.data.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.wcsm.wcsmmusicplayer.domain.model.Music

class Converters {
    @TypeConverter
    fun fromMusicList(musicList: List<Music>?): String {
        return Gson().toJson(musicList)
    }

    @TypeConverter
    fun toMusicList(json: String): List<Music>? {
        val type = object : TypeToken<List<Music>>() {}.type
        return Gson().fromJson(json, type)
    }
}