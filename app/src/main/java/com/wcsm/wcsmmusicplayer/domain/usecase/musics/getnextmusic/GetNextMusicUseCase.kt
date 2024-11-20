package com.wcsm.wcsmmusicplayer.domain.usecase.musics.getnextmusic

import android.util.Log
import com.wcsm.wcsmmusicplayer.domain.model.Music
import com.wcsm.wcsmmusicplayer.domain.usecase.MusicError

class GetNextMusicUseCase : IGetNextMusicUseCase {

    override fun invoke(musics: List<Music>, actualMusic: Music): Result<Music> {
        Log.i("#-# TESTE - GetNextMusicUseCase #-#", "musics: $musics")
        Log.i("#-# TESTE - GetNextMusicUseCase #-#", "actualMusic: $actualMusic")
        if (musics.isNotEmpty()) {
            val actualMusicIndex = musics.indexOf(actualMusic)
            if (actualMusicIndex != -1) {
                val lastMusicIndex = musics.size - 1
                Log.i("#-# TESTE - GetNextMusicUseCase #-#", "actualMusicIndex == lastMusicIndex: ${actualMusicIndex == lastMusicIndex}")
                Log.i("#-# TESTE - GetNextMusicUseCase #-#", "actualMusicIndex : $actualMusicIndex")
                Log.i("#-# TESTE - GetNextMusicUseCase #-#", "lastMusicIndex: $lastMusicIndex")
                return if(actualMusicIndex == lastMusicIndex) {
                    Result.success(musics[0]) // Go back to first music
                } else {
                    Result.success(musics[actualMusicIndex + 1]) // Go to next music
                }
            } else {
                return Result.failure(MusicError.MusicNotFoundInMusicsList("GetNextMusicUseCase - Música atual não encontrada na lista de músicas."))
            }
        } else {
            return Result.failure(MusicError.EmptyMusicsList("GetNextMusicUseCase - A lista de músicas está vazia."))
        }
    }
}