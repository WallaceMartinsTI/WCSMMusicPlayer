package com.wcsm.wcsmmusicplayer.data.mediastore

import android.net.Uri
import com.wcsm.wcsmmusicplayer.data.model.MusicFromDevice
import com.wcsm.wcsmmusicplayer.util.musicsFromDeviceList
import com.wcsm.wcsmmusicplayer.util.uriMock1
import com.wcsm.wcsmmusicplayer.util.uriMock2
import com.wcsm.wcsmmusicplayer.util.uriMock3
import com.wcsm.wcsmmusicplayer.util.uriMock4
import com.wcsm.wcsmmusicplayer.util.uriMock5
import com.wcsm.wcsmmusicplayer.util.uriMock6
import org.mockito.Mockito

class MusicMediaStoreFake : IMusicMediaStore {

    override fun fetchMusicsFromDevice(): List<MusicFromDevice> {

        return musicsFromDeviceList
    }

}