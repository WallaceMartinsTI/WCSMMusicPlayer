package com.wcsm.wcsmmusicplayer.domain.usecase.musics.getmusics

import android.net.Uri
import com.wcsm.wcsmmusicplayer.domain.model.Music
import com.wcsm.wcsmmusicplayer.domain.usecase.musics.getmusics.IGetMusicsUseCase
import com.wcsm.wcsmmusicplayer.util.musicsList
import org.mockito.Mockito

class GetMusicsUseCaseFake : IGetMusicsUseCase {

    override suspend fun invoke(): List<Music> {
        return musicsList
    }
}