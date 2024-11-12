package com.wcsm.wcsmmusicplayer.domain.usecase

import com.wcsm.wcsmmusicplayer.domain.model.Music

interface IGetMusicsUseCase {

    suspend operator fun invoke() : List<Music>

}