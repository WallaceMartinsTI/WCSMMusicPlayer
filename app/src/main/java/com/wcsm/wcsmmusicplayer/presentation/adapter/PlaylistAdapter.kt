package com.wcsm.wcsmmusicplayer.presentation.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.wcsm.wcsmmusicplayer.databinding.PlaylistItemBinding
import com.wcsm.wcsmmusicplayer.domain.model.Playlist

class PlaylistAdapter(
    private val onEdit: (playlist: Playlist) -> Unit,
    private val onDelete: (playlist: Playlist) -> Unit,
    private val onClick: () -> Unit,
) : Adapter<PlaylistAdapter.PlaylistViewHolder>() {

    private var playlistsList = emptyList<Playlist>()

    @SuppressLint("NotifyDataSetChanged")
    fun updatePlaylistsList(playlistList: List<Playlist>) {
        playlistsList = playlistList
        notifyDataSetChanged()
    }

    init {
        Log.i("#-# TESTE #-#", "PLAYLIST ADAPTER INIT")
    }

    inner class PlaylistViewHolder(
        private val binding: PlaylistItemBinding
    ) : ViewHolder(binding.root) {
        fun bind(playlist: Playlist) {
            binding.itemPlaylistName.text = playlist.title
            binding.itemPlaylistMusics.text = playlist.musics.size.toString()

            binding.itemPlaylistBtnEdit.setOnClickListener {
                onEdit(playlist)
            }

            binding.itemPlaylistBtnDelete.setOnClickListener {
                onDelete(playlist)
            }

            binding.cdPlaylistItem.setOnClickListener {
                onClick()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = PlaylistItemBinding.inflate(layoutInflater, parent, false)
        return PlaylistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        val playlistItem = playlistsList[position]
        holder.bind(playlistItem)
    }

    override fun getItemCount(): Int {
        return playlistsList.size
    }

}