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
    fun fromUri(uri: Uri?): String? {
        return uri?.toString()  // Convertendo Uri para String
    }

    @TypeConverter
    fun toUri(uriString: String?): Uri? {
        return Uri.parse(uriString)  // Convertendo String para Uri
    }
}