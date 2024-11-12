package com.wcsm.wcsmmusicplayer.domain.usecase

import com.wcsm.wcsmmusicplayer.domain.model.Music
import com.wcsm.wcsmmusicplayer.domain.repository.IMusicRepository
import javax.inject.Inject

class GetMusicsUseCase @Inject constructor(
    private val musicRepository: IMusicRepository
) : IGetMusicsUseCase {

    override suspend operator fun invoke() : List<Music> {
        // Business Rules Here ...
        return musicRepository.getMusicsFromData()
    }

}