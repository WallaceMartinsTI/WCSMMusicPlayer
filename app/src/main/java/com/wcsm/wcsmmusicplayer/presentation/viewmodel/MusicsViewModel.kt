package com.wcsm.wcsmmusicplayer.presentation.viewmodel

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wcsm.wcsmmusicplayer.domain.model.Music
import com.wcsm.wcsmmusicplayer.domain.usecase.GetMusicsUseCase
import com.wcsm.wcsmmusicplayer.domain.usecase.IGetMusicsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MusicsViewModel @Inject constructor(
    private val getMusicsUseCase: IGetMusicsUseCase
) : ViewModel() {

    private var mediaPlayer: MediaPlayer? = null

    private val _musics = MutableLiveData<List<Music>>()
    val musics: LiveData<List<Music>> get() = _musics

    // DANDO ERRO NO TESTE
    /*init {
        Log.i("#-# TESTE #-#", "MusicsViewModel - init")
        //getMusics()
    }*/

    fun getMusics() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val musicsList = getMusicsUseCase()
                _musics.postValue(musicsList)
            } catch (e: Exception) {
                Log.e("# MusicsViewModel - getMusics #", "Error trying to getMusics", e)
            }

        }
    }

    fun playMusic(context: Context, uri: Uri) {
        if(mediaPlayer == null) {
            initMediaPlayer(context, uri)
        }
        mediaPlayer?.start()
    }

    fun turnOffMediaPlayer() {
        if(mediaPlayer != null) {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }

    private fun initMediaPlayer(context: Context, uri: Uri) {
        mediaPlayer = MediaPlayer.create(
            context,
            uri
        )
    }

}