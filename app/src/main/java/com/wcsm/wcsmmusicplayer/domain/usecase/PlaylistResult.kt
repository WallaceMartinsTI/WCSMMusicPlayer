package com.wcsm.wcsmmusicplayer.domain.usecase

sealed class PlaylistResult {
    data class Success(val message: String, val crudAction: CrudAction) : PlaylistResult()
    data class Error(val message: String, val crudAction: CrudAction, val type: ErrorType) : PlaylistResult()

    enum class CrudAction {
        ADD_PLAYLIST, DELETE_PLAYLIST
    }

    enum class ErrorType {
        EMPTY_PLAYLIST, INVALID_TITLE, UNKNOWN
    }
}
