package com.wcsm.wcsmmusicplayer.domain.usecase.getnextmusic

import com.wcsm.wcsmmusicplayer.domain.model.Music

class GetNextMusicUseCaseFake : IGetNextMusicUseCase {
    override fun invoke(musics: List<Music>, actualMusic: Music): Result<Music> {
        TODO("Not yet implemented")
    }
}