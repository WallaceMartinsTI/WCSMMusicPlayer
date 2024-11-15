package com.wcsm.wcsmmusicplayer.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wcsm.wcsmmusicplayer.domain.model.Playlist

class PlaylistsViewModel : ViewModel() {

    private val _playlists = MutableLiveData<List<Playlist>>()
    val playlists: LiveData<List<Playlist>> get() = _playlists


    fun checkIfPlaylistAlreadyExists() {}

    fun savePlayList() {}

    fun getPlaylists() {}

}