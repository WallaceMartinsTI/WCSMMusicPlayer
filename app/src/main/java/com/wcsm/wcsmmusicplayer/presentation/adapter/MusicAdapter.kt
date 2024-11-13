package com.wcsm.wcsmmusicplayer.presentation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.wcsm.wcsmmusicplayer.R
import com.wcsm.wcsmmusicplayer.databinding.MusicItemBinding
import com.wcsm.wcsmmusicplayer.domain.model.Music
import com.wcsm.wcsmmusicplayer.utils.formatDurationIntToString
import java.util.Locale

class MusicAdapter(
    val context: Context,
    val onPlay: (musicTest: Music) -> Unit
) : Adapter<MusicAdapter.MusicViewHolder>() {

    private var musicsList = emptyList<Music>()
    private var currentMusic: Music? = null

    @SuppressLint("NotifyDataSetChanged")
    fun updateMusicsList(musicTests: List<Music>) {
        musicsList = musicTests
        notifyDataSetChanged()
    }

    fun updateCurrentMusic(newCurrentMusic: Music?) {
        val previousMusic = currentMusic
        currentMusic = newCurrentMusic
        //notifyDataSetChanged()
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

    inner class MusicViewHolder(
        private val binding: MusicItemBinding
    ) : ViewHolder(binding.root) {
        fun bind(music: Music) {
            binding.textMusicTitle.text = music.title
            binding.textMusicArtist.text = music.artist
            binding.textMusicAlbum.text = music.album
            binding.textMusicDuration.text = formatDurationIntToString(music.duration)

            binding.clMusicItem.setOnClickListener {
                onPlay(music)
            }

            setDefaultColors()

            if(music.uri == currentMusic?.uri) {
                setPlayingMusicColors()
            }
        }

        private fun setPlayingMusicColors() {
            val playingColor = ContextCompat.getColor(context, R.color.primary)
            binding.textMusicTitle.setTextColor(playingColor)
            binding.ivMusic.imageTintList = ColorStateList.valueOf(playingColor)
            binding.textMusicArtist.setTextColor(playingColor)
            binding.textMusicAlbum.setTextColor(playingColor)
            binding.textMusicDuration.setTextColor(playingColor)
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