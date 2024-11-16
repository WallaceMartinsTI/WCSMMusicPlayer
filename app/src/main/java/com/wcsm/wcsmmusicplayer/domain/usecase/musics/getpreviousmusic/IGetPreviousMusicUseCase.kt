package com.wcsm.wcsmmusicplayer.domain.usecase.musics.getpreviousmusic

import com.wcsm.wcsmmusicplayer.domain.model.Music

interface IGetPreviousMusicUseCase {

    operator fun invoke(musics: List<Music>, actualMusic: Music) : Result<Music>

}