package com.wcsm.wcsmmusicplayer.domain.usecase

sealed class MusicError(message: String) : Throwable(message) {
    class MusicNotFoundInMusicsList(message: String) : MusicError(message)
    class EmptyMusicsList(message: String) : MusicError(message)
    class UnknownError(message: String) : MusicError(message)
}