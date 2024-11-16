package com.wcsm.wcsmmusicplayer.domain.usecase.musics.getpreviousmusic

import com.wcsm.wcsmmusicplayer.domain.model.Music
import com.wcsm.wcsmmusicplayer.domain.usecase.MusicError

class GetPreviousMusicUseCase : IGetPreviousMusicUseCase {
    override fun invoke(musics: List<Music>, actualMusic: Music): Result<Music> {
        if (musics.isNotEmpty()) {
            val actualMusicIndex = musics.indexOf(actualMusic)
            if (actualMusicIndex != -1) {
                val lastMusicIndex = musics.size - 1
                return if(actualMusicIndex == 0) {
                    Result.success(musics[lastMusicIndex]) // Go back to last music
                } else {
                    Result.success(musics[actualMusicIndex - 1]) // Go to previous music
                }
            } else {
                return Result.failure(MusicError.MusicNotFoundInMusicsList("GetNextMusicUseCase - Música atual não encontrada na lista de músicas."))
            }
        } else {
            return Result.failure(MusicError.EmptyMusicsList("GetNextMusicUseCase - A lista de músicas está vazia."))
        }
    }
}