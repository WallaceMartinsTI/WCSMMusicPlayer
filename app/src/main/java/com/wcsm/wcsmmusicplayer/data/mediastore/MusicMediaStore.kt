package com.wcsm.wcsmmusicplayer.data.mediastore

import android.content.ContentUris
import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import com.wcsm.wcsmmusicplayer.data.model.MusicFromDevice
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MusicMediaStore (
    private val context: Context
) : IMusicMediaStore {
    override fun fetchMusicsFromDevice(): List<MusicFromDevice> {
        val musicsList = mutableListOf<MusicFromDevice>()

        val collection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Audio.Media.getContentUri(
                MediaStore.VOLUME_EXTERNAL
            )
        } else {
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        }

        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.SIZE,
            MediaStore.Audio.Media.MIME_TYPE,
            MediaStore.Audio.Media.YEAR
        )

        val selection = "${MediaStore.Audio.Media.DURATION} >= ?"
        val selectionArgs = arrayOf(
            TimeUnit.MILLISECONDS.convert(1, TimeUnit.SECONDS).toString()
        )

        val sortOrder = "${MediaStore.Audio.Media.TITLE} ASC"

        val query = context.contentResolver.query(
            collection,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )

        query?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
            val artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
            val dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
            val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
            val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)
            val mimeTypeColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.MIME_TYPE)
            val yearColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.YEAR)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val title = cursor.getString(titleColumn)
                val artist = cursor.getString(artistColumn)
                val data = cursor.getString(dataColumn)
                val duration = cursor.getInt(durationColumn)
                val size = cursor.getString(sizeColumn) ?: "Unknown Size"
                val mimeType = cursor.getString(mimeTypeColumn) ?: "Unknown MimeType"
                val year = cursor.getString(yearColumn) ?: "Unknown Year"

                // Check if music file exists
                val file = File(data)
                if (!file.exists()) continue

                val retriever = MediaMetadataRetriever()
                try {
                    retriever.setDataSource(data)
                    val album = retriever.extractMetadata(
                        MediaMetadataRetriever.METADATA_KEY_ALBUM
                    ) ?: "Unknown Album"

                    val contentUri: Uri = ContentUris.withAppendedId(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        id
                    )

                    val musicFromDevice = MusicFromDevice(
                        uri = contentUri,
                        title = title,
                        artist = artist,
                        data = data,
                        duration = duration,
                        album = album,
                        size = size,
                        mimeType = mimeType,
                        year = year
                    )

                    musicsList += musicFromDevice

                } catch (e: IllegalArgumentException) {
                    Log.w("MusicMediaStore", "Error configuring MediaMetadataRetriever for $data")
                } finally {
                    retriever.release()
                }
            }
        }

        return musicsList
    }
}