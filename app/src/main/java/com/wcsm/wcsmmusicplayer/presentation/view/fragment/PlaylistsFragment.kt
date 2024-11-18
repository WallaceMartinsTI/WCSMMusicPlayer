package com.wcsm.wcsmmusicplayer.presentation.view.fragment

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wcsm.wcsmmusicplayer.R
import com.wcsm.wcsmmusicplayer.databinding.CreatePlaylistModalBinding
import com.wcsm.wcsmmusicplayer.databinding.FragmentPlaylistsBinding
import com.wcsm.wcsmmusicplayer.databinding.PlaylistModalBinding
import com.wcsm.wcsmmusicplayer.domain.model.Playlist
import com.wcsm.wcsmmusicplayer.domain.usecase.PlaylistResult
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
            onEdit = { playlist ->
                showCreatePlaylistModal(editingPlaylist = true, playlist = playlist) // Open modal to edit playlist
            },
            onDelete = { playlist ->
                playlistsViewModel.updatePlaylistDeleted(false, "")
                playlistsViewModel.deletePlaylist(playlist)
            },
        ) { playlist ->
            /* onClick */
            //val intent = Intent(requireContext(), SelectedPlaylistActivity::class.java)
            //intent.putExtra("playlist", playlist)
            //startActivity(intent)
            musicAdapter.isSelectedPlaylistModal = true
            showPlaylistSelectedModal(playlist)
        }

        playlistsViewModel.getPlaylists()

        playlistsViewModel.playlists.observe(viewLifecycleOwner) { playlists ->
            if(playlists != null) {
                if(playlists.isNotEmpty()) {
                    playlistAdapter.updatePlaylistsList(playlists)
                    handleNoPlaylistViews(true)
                } else {
                    handleNoPlaylistViews(false)
                }
            }
        }

        playlistsViewModel.playlistDeleted.observe(viewLifecycleOwner) { playlistDeleted ->
            if(playlistDeleted.first) {
                showToastMessage(playlistDeleted.second)
                playlistsViewModel.updatePlaylistDeleted(false, "")
            }
        }


        binding.rvPlaylists.adapter = playlistAdapter
        binding.rvPlaylists.layoutManager = LinearLayoutManager(context)
        binding.rvPlaylists.addItemDecoration(
            DividerItemDecoration(context, RecyclerView.VERTICAL)
        )

        binding.fabCreatePlaylist.setOnClickListener {
            showCreatePlaylistModal(editingPlaylist = false, playlist = null)
        }

        musicAdapter = MusicAdapter(this) { music ->
            musicAdapter.checkMusicToBeAddToPlaylist(music)
        }

        musicsViewModel.musics.observe(viewLifecycleOwner) { musics ->
            musicAdapter.updateMusicsList(musics)
        }

        return binding.root
    }

    private fun handleNoPlaylistViews(hasPlaylist: Boolean) {
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

    private fun showPlaylistSelectedModal(playlist: Playlist) {
        Log.i("#-# TESTE #-#", "showPlaylistSelectedModal - playlist: $playlist")
        Log.i("#-# TESTE #-#", "showPlaylistSelectedModal - playlist.musics: ${playlist.musics}")
        val binding = PlaylistModalBinding.inflate(LayoutInflater.from(context))

        val dialog = AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        binding.rvPlaylistSelected.adapter = musicAdapter
        binding.rvPlaylistSelected.layoutManager = LinearLayoutManager(context)
        binding.rvPlaylistSelected.addItemDecoration(
            DividerItemDecoration(context, RecyclerView.VERTICAL)
        )

        musicAdapter.updateMusicsList(playlist.musics)

        binding.fabPlaylistSelectedExit.setOnClickListener {
            dialog.dismiss()
        }

        dialog.setOnDismissListener {
            musicAdapter.isSelectedPlaylistModal = false
        }

        dialog.show()
    }

    private fun showCreatePlaylistModal(editingPlaylist: Boolean, playlist: Playlist?) {
        val binding = CreatePlaylistModalBinding.inflate(LayoutInflater.from(context))

        val dialog = AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .create()

        binding.rvMusicsToAddToPlaylist.adapter = musicAdapter
        binding.rvMusicsToAddToPlaylist.layoutManager = LinearLayoutManager(context)
        binding.rvMusicsToAddToPlaylist.addItemDecoration(
            DividerItemDecoration(context, RecyclerView.VERTICAL)
        )

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        if(editingPlaylist && playlist != null) {
            binding.modalPlaylistName.setText(playlist.title)
        }

        binding.fabSaveNewPlaylist.setOnClickListener {
            binding.modalPlaylistInputLayout.error = null
            binding.modalPlaylistErrorMessage.error = null
            binding.rvMusicsToAddToPlaylist.setBackgroundResource(R.drawable.recycler_view_border)

            val musicsList = musicAdapter.getNewPlaylistMusics()
            val playlistName = binding.modalPlaylistName.text.toString()

            val newPlaylist = Playlist(title = playlistName, musics =  musicsList)

            playlistsViewModel.savePlaylist(newPlaylist)
        }

        playlistsViewModel.crudActionResponse.observe(viewLifecycleOwner) { result ->
            if(result != null) {
                when (result) {
                    is PlaylistResult.Success -> {
                        showToastMessage(result.message)
                        binding.modalPlaylistInputLayout.error = null
                        binding.rvMusicsToAddToPlaylist.setBackgroundResource(R.drawable.recycler_view_border)
                        dialog.dismiss()
                    }
                    is PlaylistResult.Error -> {
                        when(result.type) {
                            PlaylistResult.ErrorType.INVALID_TITLE -> {
                                binding.modalPlaylistErrorMessage.text = null
                                binding.rvMusicsToAddToPlaylist.setBackgroundResource(R.drawable.recycler_view_border)

                                binding.modalPlaylistInputLayout.error = result.message
                            }
                            PlaylistResult.ErrorType.EMPTY_PLAYLIST -> {
                                binding.modalPlaylistInputLayout.error = null

                                binding.modalPlaylistErrorMessage.text = result.message
                                binding.rvMusicsToAddToPlaylist.setBackgroundResource(R.drawable.error_border)
                            }

                            PlaylistResult.ErrorType.UNKNOWN -> {
                                binding.modalPlaylistInputLayout.error = null
                                binding.modalPlaylistErrorMessage.text = null

                                showToastMessage("Erro desconhecido.")
                            }
                        }
                    }
                }
            } else {
                binding.modalPlaylistInputLayout.error = null
                binding.modalPlaylistErrorMessage.error = null
                binding.rvMusicsToAddToPlaylist.setBackgroundResource(R.drawable.recycler_view_border)
            }
        }

        binding.fabCancelNewPlaylist.setOnClickListener {
            dialog.dismiss()
        }

        dialog.setOnDismissListener {
            playlistsViewModel.resetCrudActionResponse()
        }

        dialog.show()
    }

    private fun showToastMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}