package com.wcsm.wcsmmusicplayer.domain.usecase.getnextmusic

import android.net.Uri
import com.google.common.truth.Truth.assertThat
import com.wcsm.wcsmmusicplayer.domain.model.Music
import com.wcsm.wcsmmusicplayer.domain.usecase.MusicError
import com.wcsm.wcsmmusicplayer.util.musicsList
import com.wcsm.wcsmmusicplayer.util.uriMock2
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class GetNextMusicUseCaseTest {

    private lateinit var getNextMusicUseCase: GetNextMusicUseCase

    @Before
    fun setUp() {
        getNextMusicUseCase = GetNextMusicUseCase()
    }

    @Test
    fun `invoke should return the next music`() {
        val firstMusic = musicsList[0] // Music 1 -> Music 2
        val currentMusic = musicsList[3] // Music 4 -> Music 5

        val nextMusicForFirst = getNextMusicUseCase(musicsList, firstMusic)
        val nextMusicForCurrent = getNextMusicUseCase(musicsList, currentMusic)

        // First Music
        assertThat(nextMusicForFirst.isSuccess).isTrue()
        assertThat(nextMusicForFirst.getOrNull()).isEqualTo(musicsList[1])

        // CurrentMusic
        assertThat(nextMusicForCurrent.isSuccess).isTrue()
        assertThat(nextMusicForCurrent.getOrNull()).isEqualTo(musicsList[4])
    }

    @Test
    fun `invoke should return the first music`() {
        val currentMusic = musicsList[musicsList.size - 1]

        val nextMusic = getNextMusicUseCase(musicsList, currentMusic)

        assertThat(nextMusic.isSuccess).isTrue()

        assertThat(nextMusic.getOrNull()).isEqualTo(musicsList[0])
    }

    @Test
    fun `should return error when music is not found`() {
        val currentMusic = Music(uriMock2,"Musica 9","Artista 9", 23948, "Album 9", emptyList())

        val result = getNextMusicUseCase(musicsList, currentMusic)

        assertThat(result.isFailure).isTrue()

        val error = result.exceptionOrNull()

        assertThat(error).isInstanceOf(MusicError.MusicNotFoundInMusicsList::class.java)

        assertThat(
            (error as MusicError.MusicNotFoundInMusicsList).message
        ).isEqualTo(
            "GetNextMusicUseCase - Música atual não encontrada na lista de músicas."
        )
    }

    @Test
    fun `should return error when musics list is empty`() {
        val currentMusic = musicsList[1]

        val result = getNextMusicUseCase(emptyList(), currentMusic)

        assertThat(result.isFailure).isTrue()

        val error = result.exceptionOrNull()

        assertThat(error).isInstanceOf(MusicError.EmptyMusicsList::class.java)

        assertThat(
            (error as MusicError.EmptyMusicsList).message
        ).isEqualTo("GetNextMusicUseCase - A lista de músicas está vazia.")
    }

}