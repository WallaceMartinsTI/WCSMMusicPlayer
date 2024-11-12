package com.wcsm.wcsmmusicplayer.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.wcsm.wcsmmusicplayer.databinding.MusicItemBinding
import com.wcsm.wcsmmusicplayer.domain.model.Music
import java.util.Locale

class MusicAdapter(
    val onPlay: (musicTest: Music) -> Unit
) : Adapter<MusicAdapter.MusicViewHolder>() {

    private var musicsList = emptyList<Music>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateMusicsList(musicTests: List<Music>) {
        musicsList = musicTests
        notifyDataSetChanged()
    }

    inner class MusicViewHolder(
        private val binding: MusicItemBinding
    ) : ViewHolder(binding.root) {
        fun bind(musicTest: Music) {
            binding.textMusicTitle.text = musicTest.title
            binding.textMusicArtist.text = musicTest.artist
            binding.textMusicAlbum.text = musicTest.album
            binding.textMusicDuration.text = formatDuration(musicTest.duration)

            binding.clMusicItem.setOnClickListener {
                onPlay(musicTest)
            }
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

    private fun formatDuration(durationInMillis: Int) : String {
        val totalSeconds = durationInMillis / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return String.format(Locale("pt", "BR"),"%02d:%02d", minutes, seconds)
    }
}