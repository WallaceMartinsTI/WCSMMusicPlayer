package com.wcsm.wcsmmusicplayer.presentation.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wcsm.wcsmmusicplayer.data.dao.PlaylistDAO
import com.wcsm.wcsmmusicplayer.domain.model.Playlist
import com.wcsm.wcsmmusicplayer.domain.repository.IPlaylistRepository
import com.wcsm.wcsmmusicplayer.domain.usecase.PlaylistResult
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

    private val _crudActionResponse = MutableLiveData<PlaylistResult?>()
    val crudActionResponse: LiveData<PlaylistResult?> get() = _crudActionResponse

    fun getPlaylists() {
        viewModelScope.launch {
            try {
                val playlists = getPlaylistsUseCase()
                Log.i(tag, "playlist successfully fetched from playlistUseCase")
                _playlists.postValue(playlists)
            } catch (e: Exception) {
                Log.e(tag, "Error fetching playlists", e)
            }

        }
    }

    fun savePlaylist(playlist: Playlist) {
        Log.i("#-# TESTE #-#", "PlaylistsViewModel - CHAMOU: savePlaylist")
        viewModelScope.launch {
            val result = addPlaylistUseCase(playlist)
            _crudActionResponse.postValue(result)

            if(result is PlaylistResult.Success) {
                getPlaylists()
            }
        }
    }

    fun resetCrudActionResponse() {
        _crudActionResponse.value = null
    }

    /*
    fun deletePlaylist(playlist: Playlist) {
        viewModelScope.launch {
            playlistRepository.deletePlaylist(playlist)
        }
    }*/

}