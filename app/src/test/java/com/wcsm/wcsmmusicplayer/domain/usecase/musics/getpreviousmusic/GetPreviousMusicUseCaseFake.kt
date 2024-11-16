package com.wcsm.wcsmmusicplayer.domain.usecase.musics.getpreviousmusic

import com.wcsm.wcsmmusicplayer.domain.model.Music
import com.wcsm.wcsmmusicplayer.domain.usecase.musics.getpreviousmusic.IGetPreviousMusicUseCase

class GetPreviousMusicUseCaseFake : IGetPreviousMusicUseCase {
    override fun invoke(musics: List<Music>, actualMusic: Music): Result<Music> {
        TODO("Not yet implemented")
    }
}