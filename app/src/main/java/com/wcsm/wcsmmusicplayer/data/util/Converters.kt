package com.wcsm.wcsmmusicplayer.data.util

import android.net.Uri
import android.util.Log
import androidx.room.TypeConverter
import com.google.gson.Gson

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.wcsm.wcsmmusicplayer.domain.model.Music

class Converters {

    init {
        Log.i("#-# TESTE #-#", "Converters - INIT")
    }

    @TypeConverter
    fun fromMusicList(musicList: List<Music>?): String {
        Log.i("#-# TESTE #-#", "Converters - fromMusicList")
        return Gson().toJson(musicList)  // Converte a lista de Music para uma String JSON
    }

    @TypeConverter
    fun toMusicList(json: String): List<Music>? {
        val type = object : TypeToken<List<Music>>() {}.type
        return Gson().fromJson(json, type)  // Converte a String JSON de volta para uma lista de Music
    }
}