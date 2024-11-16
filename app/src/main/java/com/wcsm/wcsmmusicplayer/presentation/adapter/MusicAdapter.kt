package com.wcsm.wcsmmusicplayer.presentation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.wcsm.wcsmmusicplayer.R
import com.wcsm.wcsmmusicplayer.databinding.MusicItemBinding
import com.wcsm.wcsmmusicplayer.domain.model.Music
import com.wcsm.wcsmmusicplayer.presentation.view.activity.MusicsActivity
import com.wcsm.wcsmmusicplayer.presentation.view.fragment.MusicsFragment
import com.wcsm.wcsmmusicplayer.presentation.view.fragment.PlaylistsFragment
import com.wcsm.wcsmmusicplayer.util.formatDurationIntToString

class MusicAdapter(
    val fragment: Fragment,
    val onClick: (music: Music) -> Unit,
) : Adapter<MusicAdapter.MusicViewHolder>() {

    private var musicsList = emptyList<Music>()
    private var musicsToBeAddedToPlaylist = mutableListOf<Music>()

    private var currentPlayingSong: Music? = null
    private var musicStopped: Boolean? = null

    private val context = fragment.requireContext()

    @SuppressLint("NotifyDataSetChanged")
    fun updateMusicsList(musicList: List<Music>) {
        musicsList = musicList
        notifyDataSetChanged()
    }

    fun checkMusicToBeAddToPlaylist(music: Music) {
        val checkedMusicIndex = musicsList.indexOf(music)
        if(checkedMusicIndex != -1) {
            music.isCheckedToAddToPlaylist = !music.isCheckedToAddToPlaylist
            val isMusicChecked = music.isCheckedToAddToPlaylist
            if(isMusicChecked) {
                musicsToBeAddedToPlaylist.add(music)
            } else {
                val uncheckedMusicIndex = musicsToBeAddedToPlaylist.indexOf(music)
                if(uncheckedMusicIndex != -1) {
                    musicsToBeAddedToPlaylist.removeAt(uncheckedMusicIndex)
                }
            }
            notifyItemChanged(checkedMusicIndex)
        }
    }

    fun getNewPlaylistMusics() : List<Music> {
        return musicsToBeAddedToPlaylist.toList()
    }

    fun updateCurrentMusic(newCurrentMusic: Music?) {
        val previousMusic = currentPlayingSong
        currentPlayingSong = newCurrentMusic
        if (previousMusic != null) {
            val previousPosition = musicsList.indexOf(previousMusic)
            if (previousPosition != -1) {
                notifyItemChanged(previousPosition)
            }
        }

        if (newCurrentMusic != null) {
            val newPosition = musicsList.indexOf(newCurrentMusic)
            if (newPosition != -1) {
                notifyItemChanged(newPosition)
            }
        }
    }

    fun setMusicStopped(music: Music?) {
        if(music != null) {
            val stoppedMusic = musicsList.indexOf(music)
            if(stoppedMusic != -1) {
                musicStopped = true
                notifyItemChanged(stoppedMusic)
            }
        } else {
            musicStopped = false
        }
    }

    inner class MusicViewHolder(
        private val binding: MusicItemBinding
    ) : ViewHolder(binding.root) {
        fun bind(music: Music) {
            binding.textMusicTitle.text = music.title
            binding.textMusicArtist.text = music.artist
            binding.textMusicAlbum.text = music.album
            binding.textMusicDuration.text = formatDurationIntToString(music.duration)

            binding.clMusicItem.setOnClickListener {
                onClick(music)
            }

            setDefaultColors()

            if(music.uri == currentPlayingSong?.uri) {
                setPlayingMusicColors()
            }

            if(fragment is PlaylistsFragment) {
                if(music.isCheckedToAddToPlaylist) {
                    setPlayingMusicColors()
                } else {
                    setDefaultColors()
                }
            }

            if(musicStopped != null && musicStopped == true) {
                setDefaultColors()
            }
        }

        fun setDefaultColors() {
            val defaultColor = ContextCompat.getColor(context, R.color.on_surface)
            binding.textMusicTitle.setTextColor(defaultColor)
            binding.ivMusic.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(context, R.color.black)
            )
            binding.textMusicArtist.setTextColor(defaultColor)
            binding.textMusicAlbum.setTextColor(defaultColor)
            binding.textMusicDuration.setTextColor(defaultColor)
        }

        private fun setPlayingMusicColors() {
            val playingColor = ContextCompat.getColor(context, R.color.primary)
            binding.textMusicTitle.setTextColor(playingColor)
            binding.ivMusic.imageTintList = ColorStateList.valueOf(playingColor)
            binding.textMusicArtist.setTextColor(playingColor)
            binding.textMusicAlbum.setTextColor(playingColor)
            binding.textMusicDuration.setTextColor(playingColor)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MusicItemBinding.inflate(layoutInflater, parent, false)
        return MusicViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        val musicItem = musicsList[position]
        holder.bind(musicItem)
    }

    override fun getItemCount(): Int {
        return musicsList.size
    }

    override fun onViewRecycled(holder: MusicViewHolder) {
        super.onViewRecycled(holder)
        holder.setDefaultColors()
    }

}