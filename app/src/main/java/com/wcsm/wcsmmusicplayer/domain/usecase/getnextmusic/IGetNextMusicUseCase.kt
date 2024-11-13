package com.wcsm.wcsmmusicplayer.domain.usecase.getnextmusic

import com.wcsm.wcsmmusicplayer.domain.model.Music

interface IGetNextMusicUseCase {

    operator fun invoke(musics: List<Music>, actualMusic: Music) : Result<Music>

}