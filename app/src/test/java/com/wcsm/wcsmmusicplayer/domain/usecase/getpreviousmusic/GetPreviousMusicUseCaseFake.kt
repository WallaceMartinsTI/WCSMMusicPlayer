package com.wcsm.wcsmmusicplayer.domain.usecase.getpreviousmusic

import com.wcsm.wcsmmusicplayer.domain.model.Music

class GetPreviousMusicUseCaseFake : IGetPreviousMusicUseCase {
    override fun invoke(musics: List<Music>, actualMusic: Music): Result<Music> {
        TODO("Not yet implemented")
    }
}