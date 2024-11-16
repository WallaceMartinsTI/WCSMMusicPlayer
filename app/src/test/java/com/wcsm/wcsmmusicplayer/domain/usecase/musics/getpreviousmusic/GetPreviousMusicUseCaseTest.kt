package com.wcsm.wcsmmusicplayer.domain.usecase.musics.getpreviousmusic

import com.google.common.truth.Truth
import com.wcsm.wcsmmusicplayer.domain.model.Music
import com.wcsm.wcsmmusicplayer.domain.usecase.MusicError
import com.wcsm.wcsmmusicplayer.domain.usecase.musics.getpreviousmusic.GetPreviousMusicUseCase
import com.wcsm.wcsmmusicplayer.util.musicsList
import com.wcsm.wcsmmusicplayer.util.uriMock2
import org.junit.Before
import org.junit.Test

class GetPreviousMusicUseCaseTest {

    private lateinit var getPreviousMusicUseCase: GetPreviousMusicUseCase

    @Before
    fun setUp() {
        getPreviousMusicUseCase = GetPreviousMusicUseCase()
    }

    @Test
    fun `invoke should return the previous music`() {
        val firstMusic = musicsList[0] // Music 1 -> Music 6
        val currentMusic = musicsList[3] // Music 4 -> Music 3

        val previousMusicToLast = getPreviousMusicUseCase(musicsList, firstMusic)
        val previousMusicForCurrent = getPreviousMusicUseCase(musicsList, currentMusic)

        // First Music
        Truth.assertThat(previousMusicToLast.isSuccess).isTrue()
        Truth.assertThat(previousMusicToLast.getOrNull()).isEqualTo(musicsList[5])

        // CurrentMusic
        Truth.assertThat(previousMusicForCurrent.isSuccess).isTrue()
        Truth.assertThat(previousMusicForCurrent.getOrNull()).isEqualTo(musicsList[2])
    }

    @Test
    fun `invoke should return the last music`() {
        val currentMusic = musicsList[0]

        val previousMusic = getPreviousMusicUseCase(musicsList, currentMusic)

        Truth.assertThat(previousMusic.isSuccess).isTrue()

        Truth.assertThat(previousMusic.getOrNull()).isEqualTo(musicsList[5])
    }

    @Test
    fun `should return error when music is not found`() {
        val currentMusic = Music("uri9","Musica 9","Artista 9", 23948, "Album 9", emptyList())

        val result = getPreviousMusicUseCase(musicsList, currentMusic)

        Truth.assertThat(result.isFailure).isTrue()

        val error = result.exceptionOrNull()

        Truth.assertThat(error).isInstanceOf(MusicError.MusicNotFoundInMusicsList::class.java)

        Truth.assertThat(
            (error as MusicError.MusicNotFoundInMusicsList).message
        ).isEqualTo(
            "GetNextMusicUseCase - Música atual não encontrada na lista de músicas."
        )
    }

    @Test
    fun `should return error when musics list is empty`() {
        val currentMusic = musicsList[1]

        val result = getPreviousMusicUseCase(emptyList(), currentMusic)

        Truth.assertThat(result.isFailure).isTrue()

        val error = result.exceptionOrNull()

        Truth.assertThat(error).isInstanceOf(MusicError.EmptyMusicsList::class.java)

        Truth.assertThat(
            (error as MusicError.EmptyMusicsList).message
        ).isEqualTo("GetNextMusicUseCase - A lista de músicas está vazia.")
    }

}