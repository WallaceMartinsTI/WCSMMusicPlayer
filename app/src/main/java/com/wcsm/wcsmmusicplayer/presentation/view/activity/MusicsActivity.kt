package com.wcsm.wcsmmusicplayer.presentation.view.activity

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.TextViewCompat.setCompoundDrawableTintList
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.wcsm.wcsmmusicplayer.R
import com.wcsm.wcsmmusicplayer.databinding.ActivityMusicsBinding
import com.wcsm.wcsmmusicplayer.domain.model.Music
import com.wcsm.wcsmmusicplayer.presentation.view.fragment.MusicsFragment
import com.wcsm.wcsmmusicplayer.presentation.view.fragment.PlaylistsFragment
import com.wcsm.wcsmmusicplayer.presentation.viewmodel.MusicsViewModel
import com.wcsm.wcsmmusicplayer.util.ReproductionModes
import com.wcsm.wcsmmusicplayer.util.formatDurationIntToString
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MusicsActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMusicsBinding.inflate(layoutInflater) }

    private val musicsViewModel by viewModels<MusicsViewModel>()

    private val handler = Handler(Looper.getMainLooper())
    private var updateTimeRunnable: Runnable? = null

    private var reproductionModeIndex = 0

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

        musicsViewModel.playingSong.observe(this) { playingMusic ->
            if(playingMusic != null) {
                setPlayingMusicToScreen(playingMusic)
                binding.playingMusicInclude.playingMusicSeekBar.max = playingMusic.duration
                startUpdatingCurrentTime()

                handleIncludeVisibility(true)
            }
        }

        musicsViewModel.musicPaused.observe(this) { musicPaused ->
            if(musicPaused != null) {
                val iconRes = if(musicPaused) {
                    R.drawable.ic_play_24
                } else {
                    R.drawable.ic_pause_24
                }

                binding.playingMusicInclude.playingMusicPlayPause.setImageDrawable(
                    ContextCompat.getDrawable(this, iconRes)
                )
            }
        }

        musicsViewModel.musicEnded.observe(this) { musicEnded ->
            if(musicEnded) {
                val reproductionMode = getReproductionMode()

                when(reproductionMode) {
                    ReproductionModes.SEQUENTIAL -> {
                        musicsViewModel.nextMusic(this)
                    }
                    ReproductionModes.LOOP -> {
                        musicsViewModel.restartMusic(this)
                    }
                    ReproductionModes.PLAY_ONCE -> {
                        musicsViewModel.stopMusic()
                        handleIncludeVisibility(false)
                    }
                }

                musicsViewModel.resetMusicEndedFlag()
            }
        }

        binding.playingMusicInclude.playingMusicSeekBar.setOnSeekBarChangeListener(
            object : OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    if(fromUser) {
                        musicsViewModel.seekPlayingMusicTo(progress)
                    }
                }
                override fun onStartTrackingTouch(seekBar: SeekBar?) { }
                override fun onStopTrackingTouch(seekBar: SeekBar?) { }
            }
        )

        initControls()
    }

    private fun initControls() {
        with(binding.playingMusicInclude) {
            playingMusicMode.setOnClickListener {
                changeReproductionMode()
            }

            playingMusicPrevious.setOnClickListener {
                musicsViewModel.previousMusic(this@MusicsActivity)
            }

            playingMusicPlayPause.setOnClickListener {
                musicsViewModel.playOrPause(this@MusicsActivity)
            }

            playingMusicNext.setOnClickListener {
                musicsViewModel.nextMusic(this@MusicsActivity)
            }

            playingMusicStop.setOnClickListener {
                musicsViewModel.stopMusic()
                handleIncludeVisibility(false)
            }
        }

        handleReproductionModes()
    }

    private fun handleIncludeVisibility(isVisible: Boolean) {
        if(isVisible) {
            binding.playingMusicInclude.clPlayingMusicScreen.visibility = View.VISIBLE
        } else {
            binding.playingMusicInclude.clPlayingMusicScreen.visibility = View.GONE
        }
    }

    private fun changeReproductionMode() {
        reproductionModeIndex = (reproductionModeIndex + 1) % ReproductionModes.entries.size
        val newMode = ReproductionModes.entries[reproductionModeIndex]

        setReproductionMode(newMode)
        updateReproductionModeIcon(newMode)
    }

    private fun handleReproductionModes() {
        val savedReproductionMode = getReproductionMode()

        reproductionModeIndex = savedReproductionMode.ordinal
        updateReproductionModeIcon(savedReproductionMode)
    }

    private fun updateReproductionModeIcon(mode: ReproductionModes) {
        val iconRes = when (mode) {
            ReproductionModes.SEQUENTIAL -> R.drawable.ic_sequential_24
            ReproductionModes.LOOP -> R.drawable.ic_repeat_one_24
            ReproductionModes.PLAY_ONCE -> R.drawable.ic_play_once_24
        }
        binding.playingMusicInclude.playingMusicMode.setImageDrawable(
            ContextCompat.getDrawable(this, iconRes)
        )
    }

    private fun getReproductionMode() : ReproductionModes {
        val sharedPreferences = this.getSharedPreferences("WCSMMediaPlayerPublic", Context.MODE_PRIVATE)
        val modeString = sharedPreferences.getString("reproductionMode", null)

        return try {
            modeString?.let { ReproductionModes.valueOf(it) } ?: ReproductionModes.SEQUENTIAL
        } catch (e: Exception) {
            Log.e("# MusicsActivity #", "Erro ao recuperar modo de reprodução: ${e.message}")
            ReproductionModes.SEQUENTIAL
        }
    }

    private fun setReproductionMode(mode: ReproductionModes) {
        val sharedPreferences = this.getSharedPreferences("WCSMMediaPlayerPublic", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("reproductionMode", mode.toString()).apply()
    }

    private fun initMusicsFragment() {
        supportFragmentManager.commit {
            replace<MusicsFragment>(binding.fcvMusics.id)
        }
        binding.btnOpenMusicsFragment.setTextColor(
            ContextCompat.getColor(this, R.color.secondary_dark)
        )
        setCompoundDrawableTintList(binding.btnOpenMusicsFragment, ColorStateList.valueOf(
            ContextCompat.getColor(this, R.color.secondary_dark)
        ))

        binding.btnOpenPlaylistsFragment.setTextColor(
            ContextCompat.getColor(this, R.color.white)
        )
        setCompoundDrawableTintList(binding.btnOpenPlaylistsFragment, ColorStateList.valueOf(
            ContextCompat.getColor(this, R.color.white)
        ))
    }

    private fun initPlaylistsFragment() {
        supportFragmentManager.commit {
            replace<PlaylistsFragment>(binding.fcvMusics.id)
        }
        binding.btnOpenPlaylistsFragment.setTextColor(
            ContextCompat.getColor(this, R.color.secondary_dark)
        )
        setCompoundDrawableTintList(binding.btnOpenPlaylistsFragment, ColorStateList.valueOf(
            ContextCompat.getColor(this, R.color.secondary_dark)
        ))

        binding.btnOpenMusicsFragment.setTextColor(
            ContextCompat.getColor(this, R.color.white)
        )
        setCompoundDrawableTintList(binding.btnOpenMusicsFragment, ColorStateList.valueOf(
            ContextCompat.getColor(this, R.color.white)
        ))
    }

    private fun startUpdatingCurrentTime() {
        updateTimeRunnable?.let { handler.removeCallbacks(it) }

        updateTimeRunnable = object : Runnable {
            override fun run() {
                val currentPosition = musicsViewModel.playingMusicCurrentPosition()
                setPlayingMusicActualTime(currentPosition)
                binding.playingMusicInclude.playingMusicSeekBar.progress = currentPosition
                handler.postDelayed(this, 1000)
            }
        }

        handler.post(updateTimeRunnable!!)
    }

    private fun setPlayingMusicActualTime(actualTime: Int) {
        binding.playingMusicInclude.playingMusicAtualTime.text = formatDurationIntToString(actualTime)
    }

    private fun setPlayingMusicToScreen(playingMusic: Music) {
        with(binding.playingMusicInclude) {
            playingMusicTitle.text = playingMusic.title
            playingMusicArtist.text = playingMusic.artist
            playingMusicAlbum.text = playingMusic.album
            playingMusicDuration.text = formatDurationIntToString(playingMusic.duration)
            playingMusicTotalDuration.text = formatDurationIntToString(playingMusic.duration)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        updateTimeRunnable?.let { handler.removeCallbacks(it) }
    }
}