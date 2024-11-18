package com.wcsm.wcsmmusicplayer.domain.usecase

sealed class PlaylistResult {
    data class Success(val message: String) : PlaylistResult()
    data class Error(val message: String, val type: ErrorType) : PlaylistResult()

    enum class ErrorType {
        EMPTY_PLAYLIST, INVALID_TITLE, UNKNOWN
    }
}
