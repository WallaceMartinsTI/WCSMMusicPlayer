package com.wcsm.wcsmmusicplayer.presentation.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.wcsm.wcsmmusicplayer.R
import com.wcsm.wcsmmusicplayer.databinding.ActivityMusicsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MusicsActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMusicsBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initMusicsFragment()

        binding.btnOpenMusicsFragment.setOnClickListener {
            initMusicsFragment()
        }

        binding.btnOpenPlaylistsFragment.setOnClickListener {
            initPlaylistsFragment()
        }

        //binding.playingMusicInclude
    }

    private fun initMusicsFragment() {
        supportFragmentManager.commit {
            replace<MusicsFragment>(binding.fcvMusics.id)
        }
    }

    private fun initPlaylistsFragment() {
        supportFragmentManager.commit {
            replace<PlaylistsFragment>(binding.fcvMusics.id)
        }
    }
}