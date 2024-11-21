package com.wcsm.wcsmmusicplayer.presentation.service

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.wcsm.wcsmmusicplayer.R
import com.wcsm.wcsmmusicplayer.presentation.view.activity.MusicsActivity
import com.wcsm.wcsmmusicplayer.util.Constants

class PlayingMusicService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    @SuppressLint("RemoteViewLayout")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val channelId = Constants.ID_PLAYING_SONG_NOTIFICATION_CHANNEL

        var musicTitle = ""
        var musicArtist = ""

        val bundle = intent?.extras
        if(bundle != null) {
            musicTitle = bundle.getString("musicTitle") ?: "TITLE NDA"
            musicArtist = bundle.getString("musicArtist") ?: "ARTIST NDA"
        }

        val notificationIntent = Intent(applicationContext, MusicsActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val largeIcon = BitmapFactory.decodeResource(resources, R.drawable.ic_playlist_24)
        val notification = NotificationCompat.Builder(this, channelId).apply {
            setSmallIcon(R.drawable.ic_playing_24)
            setContentTitle(musicTitle)
            setContentText(musicArtist)
            setLargeIcon(largeIcon)
            setShowWhen(false)
            setContentIntent(pendingIntent)
            setPriority(NotificationCompat.PRIORITY_HIGH)
        }.build()

        startForeground(1, notification)

        return super.onStartCommand(intent, flags, startId)
    }
}