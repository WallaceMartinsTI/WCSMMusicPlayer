package com.wcsm.wcsmmusicplayer.presentation.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wcsm.wcsmmusicplayer.databinding.FragmentMusicsBinding
import com.wcsm.wcsmmusicplayer.presentation.adapter.MusicAdapter
import com.wcsm.wcsmmusicplayer.presentation.viewmodel.MusicsViewModel

class MusicsFragment : Fragment() {

    private var _binding: FragmentMusicsBinding? = null
    private val binding get() = _binding!!

    private lateinit var musicAdapter: MusicAdapter

    private val musicsViewModel by activityViewModels<MusicsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMusicsBinding.inflate(inflater, container, false)

        musicAdapter = MusicAdapter(this) { music ->
            musicsViewModel.startMusic(requireContext(), music)
        }

        musicsViewModel.musics.observe(viewLifecycleOwner) { musics ->
            musicAdapter.updateMusicsList(musics)
        }

        musicsViewModel.playingSong.observe(viewLifecycleOwner) { playingSong ->
            if(playingSong != null) {
                musicAdapter.updateCurrentMusic(playingSong)
            }
        }

        musicsViewModel.stoppedSong.observe(viewLifecycleOwner) { stoppedSong ->
            if(stoppedSong != null) {
                musicAdapter.setMusicStopped(stoppedSong)
            } else {
                musicAdapter.setMusicStopped(null)
            }
        }

        binding.rvMusic.adapter = musicAdapter
        binding.rvMusic.layoutManager = LinearLayoutManager(context)
        binding.rvMusic.addItemDecoration(
            DividerItemDecoration(context, RecyclerView.VERTICAL)
        )

        musicsViewModel.getMusics()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}