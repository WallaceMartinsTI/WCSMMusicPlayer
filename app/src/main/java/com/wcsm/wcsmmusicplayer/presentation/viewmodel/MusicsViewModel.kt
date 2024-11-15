package com.wcsm.wcsmmusicplayer.presentation.viewmodel

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wcsm.wcsmmusicplayer.domain.model.Music
import com.wcsm.wcsmmusicplayer.domain.usecase.MusicError
import com.wcsm.wcsmmusicplayer.domain.usecase.getmusics.IGetMusicsUseCase
import com.wcsm.wcsmmusicplayer.domain.usecase.getnextmusic.IGetNextMusicUseCase
import com.wcsm.wcsmmusicplayer.domain.usecase.getpreviousmusic.IGetPreviousMusicUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MusicsViewModel @Inject constructor(
    private val getMusicsUseCase: IGetMusicsUseCase,
    private val getNextMusicUseCase: IGetNextMusicUseCase,
    private val getPreviousMusicUseCase: IGetPreviousMusicUseCase
) : ViewModel() {

    private var mediaPlayer: MediaPlayer? = null

    private val _musics = MutableLiveData<List<Music>>()
    val musics: LiveData<List<Music>> get() = _musics
    
    private val _playingMusic = MutableLiveData<Music>()
    val playingMusic: LiveData<Music> get() = _playingMusic

    private val _musicPaused = MutableLiveData<Boolean>()
    val musicPaused: LiveData<Boolean> get() = _musicPaused

    private val _musicEnded = MutableLiveData<Boolean>()
    val musicEnded: LiveData<Boolean> get() = _musicEnded

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

    fun previousMusic(context: Context) {
        getPreviousMusic(context)
    }

    fun startMusic(context: Context, music: Music) {
        if(mediaPlayer == null) {
            initMediaPlayer(context, music.uri)
        } else {
            stopPlayingMusic()
            initMediaPlayer(context, music.uri)
        }
        _playingMusic.value = music
        _musicPaused.value = false
        mediaPlayer?.start()
    }

    fun playOrPause(context: Context) {
        if(mediaPlayer == null) {
            if(playingMusic.value != null) {
                startMusic(context, playingMusic.value!!)
            }
        } else {
            if(mediaPlayer?.isPlaying == true) {
                mediaPlayer?.pause()
                _musicPaused.value = true
            } else {
                seekPlayingMusicTo(playingMusicCurrentPosition())
                _musicPaused.value = false
                mediaPlayer?.start()

                println("#-# TESTE #-# => mediaPlayer.isPlaying: ${mediaPlayer?.isPlaying}")
            }
        }
    }

    fun restartMusic(context: Context) {
        if(playingMusic.value != null) {
            startMusic(context, playingMusic.value!!)
        }
    }

    fun nextMusic(context: Context) {
        getNextMusic(context)
    }

    fun stopMusic() {
        // HIDE MUSIC SCREEN
        stopPlayingMusic()
    }

    fun playingMusicCurrentPosition() : Int {
        return mediaPlayer?.currentPosition ?: 0
    }

    fun seekPlayingMusicTo(position: Int) {
        mediaPlayer?.seekTo(position)
    }

    fun resetMusicEndedFlag() {
        _musicEnded.value = false
    }

    fun turnOffMediaPlayer() {
        if(mediaPlayer != null) {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }

    private fun initMediaPlayer(context: Context, uri: Uri) {
        mediaPlayer = MediaPlayer.create(context, uri).apply {
            setOnCompletionListener {
                _musicEnded.postValue(true)
            }
        }
    }

    private fun getPreviousMusic(context: Context) {
        val musicsList = musics.value
        val currentMusic = playingMusic.value
        if(musicsList != null && currentMusic != null) {
            val result = getPreviousMusicUseCase(musicsList, currentMusic)

            when {
                result.isSuccess -> {
                    val previousMusic = result.getOrNull()
                    if(previousMusic != null) {
                        startMusic(context, previousMusic)
                    }
                }
                result.isFailure -> {
                    val error = result.exceptionOrNull()
                    showErrorMessages(context, error)
                }
            }
        }
    }

    private fun getNextMusic(context: Context) {
        val musicsList = musics.value
        val currentMusic = playingMusic.value
        if(musicsList != null && currentMusic != null) {
            val result = getNextMusicUseCase(musicsList, currentMusic)

            when {
                result.isSuccess -> {
                    val nextMusic = result.getOrNull()
                    if(nextMusic != null) {
                        startMusic(context, nextMusic)
                    }
                }
                result.isFailure -> {
                    val error = result.exceptionOrNull()
                    showErrorMessages(context, error)
                }
            }
        }
    }

    private fun stopPlayingMusic() {
        mediaPlayer?.stop()
        mediaPlayer?.reset()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    private fun showErrorMessages(context: Context, throwable: Throwable?) {
        when(throwable) {
            is MusicError.MusicNotFoundInMusicsList -> {
                showToastMessage(context, "Música não encontrada.")
            }
            is MusicError.EmptyMusicsList -> {
                showToastMessage(context, "Lista de músicas vazia.")
            }
            is MusicError.UnknownError -> {
                showToastMessage(context, "Erro desconhecido.")
            }
        }
    }

    private fun showToastMessage(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}