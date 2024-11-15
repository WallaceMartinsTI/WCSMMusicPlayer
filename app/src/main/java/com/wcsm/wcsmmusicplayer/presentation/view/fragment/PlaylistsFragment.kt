package com.wcsm.wcsmmusicplayer.presentation.view.fragment

import android.animation.ObjectAnimator
import android.os.Bundle
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
import com.wcsm.wcsmmusicplayer.presentation.adapter.MusicAdapter
import com.wcsm.wcsmmusicplayer.presentation.viewmodel.MusicsViewModel

class PlaylistsFragment : Fragment() {

    private var _binding: FragmentPlaylistsBinding? = null
    private val binding get() = _binding!!

    private lateinit var musicAdapter: MusicAdapter

    private val musicsViewModel by activityViewModels<MusicsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaylistsBinding.inflate(inflater, container, false)

        noPlaylistItems()

        musicAdapter = MusicAdapter(requireContext()) { music ->

        }

        musicsViewModel.musics.observe(viewLifecycleOwner) { musics ->
            musicAdapter.updateMusicsList(musics)
        }

        binding.fabCreatePlaylist.setOnClickListener {
            showCreatePlaylistModal()
        }

        return binding.root
    }

    private fun showCreatePlaylistModal() {
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

        binding.fabCancelNewPlaylist.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}