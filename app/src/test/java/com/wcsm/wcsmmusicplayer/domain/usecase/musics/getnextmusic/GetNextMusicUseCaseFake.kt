package com.wcsm.wcsmmusicplayer.domain.usecase.musics.getnextmusic

import com.wcsm.wcsmmusicplayer.domain.model.Music
import com.wcsm.wcsmmusicplayer.domain.usecase.musics.getnextmusic.IGetNextMusicUseCase

class GetNextMusicUseCaseFake : IGetNextMusicUseCase {
    override fun invoke(musics: List<Music>, actualMusic: Music): Result<Music> {
        TODO("Not yet implemented")
    }
}