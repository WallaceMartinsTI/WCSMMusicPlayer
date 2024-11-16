package com.wcsm.wcsmmusicplayer.presentation.view.fragment

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wcsm.wcsmmusicplayer.databinding.CreatePlaylistModalBinding
import com.wcsm.wcsmmusicplayer.databinding.FragmentPlaylistsBinding
import com.wcsm.wcsmmusicplayer.domain.model.Playlist
import com.wcsm.wcsmmusicplayer.presentation.adapter.MusicAdapter
import com.wcsm.wcsmmusicplayer.presentation.adapter.PlaylistAdapter
import com.wcsm.wcsmmusicplayer.presentation.viewmodel.MusicsViewModel
import com.wcsm.wcsmmusicplayer.presentation.viewmodel.PlaylistsViewModel

class PlaylistsFragment : Fragment() {

    private var _binding: FragmentPlaylistsBinding? = null
    private val binding get() = _binding!!

    private lateinit var musicAdapter: MusicAdapter
    private lateinit var playlistAdapter: PlaylistAdapter

    private val musicsViewModel by activityViewModels<MusicsViewModel>()

    private val playlistsViewModel by activityViewModels<PlaylistsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaylistsBinding.inflate(inflater, container, false)

        playlistAdapter = PlaylistAdapter(
            { /* onEdit */
                Log.i("#-# TESTE #-#", "PlaylistsFragment - Adapter - ON EDIT")
            },
            { /* onDelete */
                Log.i("#-# TESTE #-#", "PlaylistsFragment - Adapter - ON DELETE")
            },
        ) {
            /* onClick */
            Log.i("#-# TESTE #-#", "PlaylistsFragment - Adapter - ON CLICK")
        }

        playlistsViewModel.getPlaylists()

        playlistsViewModel.playlists.observe(viewLifecycleOwner) { playlists ->
            Log.i("#-# TESTE #-#", "PLAYLIST-OBSERVE: playlist: $playlists")
            if(playlists != null) {
                if(playlists.isNotEmpty()) {
                    handleNoPlaylistViews(true)
                } else {
                    handleNoPlaylistViews(false)
                }
            }
        }

        binding.rvPlaylists.adapter = playlistAdapter
        binding.rvPlaylists.layoutManager = LinearLayoutManager(context)
        binding.rvPlaylists.addItemDecoration(
            DividerItemDecoration(context, RecyclerView.VERTICAL)
        )

        binding.fabCreatePlaylist.setOnClickListener {
            showCreatePlaylistModal()
        }

        return binding.root
    }

    private fun handleNoPlaylistViews(hasPlaylist: Boolean) {
        Log.i("#-# TESTE #-#", "handleNoPlaylistViews - hasPlaylist: $hasPlaylist")
        if(hasPlaylist) {
            binding.textNoPlaylist.visibility = View.GONE
            binding.textCreatePlaylistHelper.visibility = View.GONE
            binding.rvPlaylists.visibility = View.VISIBLE
        } else {
            binding.textNoPlaylist.visibility = View.VISIBLE
            binding.textCreatePlaylistHelper.visibility = View.VISIBLE
            binding.rvPlaylists.visibility = View.GONE
            noPlaylistItems()
        }
    }

    private fun noPlaylistItems() {
        Log.i("#-# TESTE #-#", "noPlaylistItems: CHAMOU A ANIMAÇÃO")
        ObjectAnimator.ofFloat(
            binding.textCreatePlaylistHelper,
            "translationX",
            50f
        ).apply {
            duration = 1000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
            start()
        }
    }

    private fun showCreatePlaylistModal() {
        val binding = CreatePlaylistModalBinding.inflate(LayoutInflater.from(context))

        val dialog = AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .create()

        musicAdapter = MusicAdapter(requireContext()) { music ->
            musicAdapter.checkMusicToBeAddToPlaylist(music)
        }

        musicsViewModel.musics.observe(viewLifecycleOwner) { musics ->
            musicAdapter.updateMusicsList(musics)
        }

        binding.rvMusicsToAddToPlaylist.adapter = musicAdapter
        binding.rvMusicsToAddToPlaylist.layoutManager = LinearLayoutManager(context)
        binding.rvMusicsToAddToPlaylist.addItemDecoration(
            DividerItemDecoration(context, RecyclerView.VERTICAL)
        )

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        binding.fabSaveNewPlaylist.setOnClickListener {
            val musicsList = musicAdapter.getNewPlaylistMusics()
            if(musicsList != null) {
                val playlistName = binding.modalPlaylistName.text.toString()
                // SE PASSAR NA VALIDACAO DO NOME DA PLAYLIST
                //val musics = MusicsList(musicsList)
                //val newPlaylist = Playlist(title = playlistName, musics =  musics)
                val newPlaylist = Playlist(title = playlistName, musics =  musicsList)
                playlistsViewModel.savePlaylist(newPlaylist)
            }
        }

        binding.fabCancelNewPlaylist.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}