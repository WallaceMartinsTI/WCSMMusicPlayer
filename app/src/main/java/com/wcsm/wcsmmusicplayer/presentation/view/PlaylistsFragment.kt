package com.wcsm.wcsmmusicplayer.presentation.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wcsm.wcsmmusicplayer.databinding.FragmentPlaylistsBinding

class PlaylistsFragment : Fragment() {

    private var _binding: FragmentPlaylistsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaylistsBinding.inflate(inflater, container, false)

        binding.imageButton.setOnClickListener {
            Log.i("#-# TESTE #-#", "IMAGE BUTTON - CLICK()")
        }

        binding.imageButtonButton.setOnClickListener {
            Log.i("#-# TESTE #-#", "IMAGE BUTTON BUTTON - CLICK()")
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}