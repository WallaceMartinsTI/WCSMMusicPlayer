package com.wcsm.wcsmmusicplayer.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wcsm.wcsmmusicplayer.domain.model.Playlist
import com.wcsm.wcsmmusicplayer.domain.usecase.PlaylistResult
import com.wcsm.wcsmmusicplayer.domain.usecase.playlists.addplaylist.IAddPlaylistUseCase
import com.wcsm.wcsmmusicplayer.domain.usecase.playlists.deleteplaylist.DeletePlaylistUseCase
import com.wcsm.wcsmmusicplayer.domain.usecase.playlists.getplaylists.IGetPlaylistsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistsViewModel @Inject constructor(
    private val getPlaylistsUseCase: IGetPlaylistsUseCase,
    private val addPlaylistUseCase: IAddPlaylistUseCase,
    private val deletePlaylistUseCase: DeletePlaylistUseCase
) : ViewModel() {

    private val tag = "# PlaylistsViewModel #"

    private val _playlists = MutableLiveData<List<Playlist>>()
    val playlists: LiveData<List<Playlist>> get() = _playlists

    private val _crudActionResponse = MutableLiveData<PlaylistResult?>()
    val crudActionResponse: LiveData<PlaylistResult?> get() = _crudActionResponse

    private val _playlistDeleted = MutableLiveData(Pair(false, ""))
    val playlistDeleted: LiveData<Pair<Boolean, String>> get() = _playlistDeleted

    private val _responseToastAlreadyShown = MutableLiveData(false)
    val responseToastAlreadyShown: LiveData<Boolean> get() = _responseToastAlreadyShown

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

    fun updateResponseToastAlreadyShown(status: Boolean) {
        _responseToastAlreadyShown.value = status
    }

    fun savePlaylist(playlist: Playlist) {
        _responseToastAlreadyShown.value = false
        viewModelScope.launch {
            val result = addPlaylistUseCase(playlist)
            _crudActionResponse.postValue(result)

            if(result is PlaylistResult.Success) {
                getPlaylists()
            }
        }
    }

    fun updatePlaylistDeleted(isDeleted: Boolean, message: String) {
        _playlistDeleted.value = Pair(isDeleted, message)
    }

    fun resetCrudActionResponse() {
        _crudActionResponse.value = null
    }

    fun deletePlaylist(playlist: Playlist) {
        _responseToastAlreadyShown.value = false
        viewModelScope.launch {
            val result = deletePlaylistUseCase(playlist)
            _crudActionResponse.postValue(result)

            if(result is PlaylistResult.Success) {
                getPlaylists()
                _playlistDeleted.value = Pair(true, result.message)
            }
        }
    }

}