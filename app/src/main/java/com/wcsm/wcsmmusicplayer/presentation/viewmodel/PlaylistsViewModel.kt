package com.wcsm.wcsmmusicplayer.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wcsm.wcsmmusicplayer.data.dao.PlaylistDAO
import com.wcsm.wcsmmusicplayer.domain.model.Playlist
import com.wcsm.wcsmmusicplayer.domain.repository.IPlaylistRepository
import com.wcsm.wcsmmusicplayer.domain.usecase.playlists.addplaylist.IAddPlaylistUseCase
import com.wcsm.wcsmmusicplayer.domain.usecase.playlists.getplaylists.IGetPlaylistsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistsViewModel @Inject constructor(
    private val getPlaylistsUseCase: IGetPlaylistsUseCase,
    private val addPlaylistUseCase: IAddPlaylistUseCase
) : ViewModel() {

    private val tag = "# PlaylistsViewModel #"

    private val _playlists = MutableLiveData<List<Playlist>>()
    val playlists: LiveData<List<Playlist>> get() = _playlists

    fun checkIfPlaylistAlreadyExists() {}

    //fun savePlayList() {}

    fun getPlaylists() {
        viewModelScope.launch {
            try {
                val playlists = getPlaylistsUseCase()
                Log.i(tag, "playlist successfully fetched from playlistUseCase")
                _playlists.postValue(playlists)
                Log.i("#-# TESTE #-#", "SETOU PLAYLIST NO POSTVALUE")
            } catch (e: Exception) {

            }

        }
    }

    fun savePlaylist(playlist: Playlist) {
        viewModelScope.launch {
            try {
                addPlaylistUseCase(playlist)
                Log.i(tag, "playlist successfully seny to addPlaylistUseCase")
            } catch (e: Exception) {
                Log.e(tag, "savePlaylist", e)
            }
        }
    }

    /*
    fun deletePlaylist(playlist: Playlist) {
        viewModelScope.launch {
            playlistRepository.deletePlaylist(playlist)
        }
    }*/
}