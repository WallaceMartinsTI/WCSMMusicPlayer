package com.wcsm.wcsmmusicplayer.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wcsm.wcsmmusicplayer.presentation.adapter.MusicAdapter
import com.wcsm.wcsmmusicplayer.databinding.FragmentMusicsBinding
import com.wcsm.wcsmmusicplayer.presentation.viewmodel.MusicsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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

        musicAdapter = MusicAdapter(requireContext()) { music ->
            musicsViewModel.startMusic(requireContext(), music)
        }

        musicsViewModel.musics.observe(viewLifecycleOwner) { musics ->
            musicAdapter.updateMusicsList(musics)
        }

        musicsViewModel.playingMusic.observe(viewLifecycleOwner) { playingMusic ->
            if(playingMusic != null) {
                musicAdapter.updateCurrentMusic(playingMusic)
            }
        }

        binding.rvMusic.adapter = musicAdapter
        binding.rvMusic.layoutManager = LinearLayoutManager(context)

        binding.rvMusic.addItemDecoration(
            DividerItemDecoration(context, RecyclerView.VERTICAL)
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        musicsViewModel.getMusics()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        musicsViewModel.turnOffMediaPlayer()
        _binding = null
    }

}