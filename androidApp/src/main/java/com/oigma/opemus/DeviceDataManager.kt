package com.oigma.opemus

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.net.toFile
import androidx.core.net.toUri
import com.oigma.opemus.data.models.Track
import java.io.File
import java.io.FileDescriptor
import java.io.FileInputStream
import java.util.concurrent.TimeUnit

/**
 * Created by Harsewak Singh on 17/01/2022.
 *
 * media, content on device
 */
interface DeviceDataManager {
    suspend fun findTracks(): List<Track>
}

// Container for information about each video.

val Track.uri: Uri
    get() {
        id.toLongOrNull()?.let {
            return ContentUris.withAppendedId(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI, it
            )
        } ?: run {
            return File(id).toUri()
        }
    }

val File.track: Track
    get() {
        return Track(absolutePath, name, 0, this.length().toInt())
    }

val Track.fd: FileDescriptor
    get() {
        return FileInputStream(uri.toFile()).fd
    }

class DeviceDataManagerImpl(private val context: Context) : DeviceDataManager {
    // Need the READ_EXTERNAL_STORAGE permission if accessing video files that your
// app didn't create.
    private val root: File = File(Environment.getExternalStorageDirectory().path)


    override suspend fun findTracks(): List<Track> {
        val tracks = mutableListOf<Track>()
        //tracks.addAll(findVideoTracks())
        //tracks.addAll(findAudioTracks())
        tracks.addAll(findMediaFiles())
        return tracks
    }

    private fun findVideoTracks(): List<Track> {
        val tracks = mutableListOf<Track>()

        val collection =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Video.Media.getContentUri(
                    MediaStore.VOLUME_EXTERNAL
                )
            } else {
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            }

        val projection = arrayOf(
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.DURATION,
            MediaStore.Video.Media.SIZE,
        )

        // Show only videos that are at least 5 minutes in duration.
        val selection = "${MediaStore.Video.Media.DURATION} >= ?"
        val selectionArgs = arrayOf(
            TimeUnit.MILLISECONDS.convert(1, TimeUnit.MINUTES).toString()
        )

        // Display videos in alphabetical order based on their display name.
        val sortOrder = "${MediaStore.Video.Media.DISPLAY_NAME} ASC"


        val query = context.contentResolver.query(
            collection,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )
        query?.use { cursor ->
            // Cache column indices.
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
            val nameColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
            val durationColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)
            val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)

            while (cursor.moveToNext()) {
                // Get values of columns for a given video.
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)
                val duration = cursor.getInt(durationColumn)
                val size = cursor.getInt(sizeColumn)

                // Stores column values and the contentUri in a local object
                // that represents the media file.
                tracks += Track(id.toString(), name, duration, size)
            }
        }
        return tracks
    }

    private fun findAudioTracks(): List<Track> {
        val collection =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Audio.Media.getContentUri(
                    MediaStore.VOLUME_EXTERNAL
                )
            } else {
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            }


        return findAudioTracks(collection)
    }

    private fun findAudioTracks(uri: Uri): List<Track> {
        val tracks = mutableListOf<Track>()

        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.SIZE,
        )

        // Show only videos that are at least 5 minutes in duration.
        val selection = "${MediaStore.Audio.Media.DURATION} >= ?"
        val selectionArgs = arrayOf(
            TimeUnit.MILLISECONDS.convert(10, TimeUnit.SECONDS).toString()
        )

        // Display videos in alphabetical order based on their display name.
        val sortOrder = "${MediaStore.Audio.Media.DISPLAY_NAME} ASC"


        val query = context.contentResolver.query(
            uri,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )
        query?.use { cursor ->
            // Cache column indices.
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val nameColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)
            val durationColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
            val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)
            while (cursor.moveToNext()) {
                // Get values of columns for a given video.
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)
                val duration = cursor.getInt(durationColumn)
                val size = cursor.getInt(sizeColumn)

                // Stores column values and the contentUri in a local object
                // that represents the media file.
                tracks += Track(id.toString(), name, duration, size)
            }
        }
        return tracks
    }


    private fun findMediaFiles(): List<Track> {
        val filesArray: MutableList<File> = arrayListOf()
        findMediaFile(root, filesArray)
        return filesArray.map { it.track }
//        val collection =
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                MediaStore.Downloads.getContentUri(MediaStore.VOLUME_EXTERNAL)
//            } else {
//                Uri.parse("content://media/external/downloads")
//            }
//        return findAudioTracks(collection)
    }

    private fun findMediaFile(file: File, files: MutableList<File>) {
        file.listFiles()?.forEach { file ->
            file?.let {
                if (it.isDirectory) {
                    findMediaFile(it, files)
                } else if (it.name.endsWith(".mp3") || it.name.endsWith(".m4a")) {
                    files.add(it)
                }
            }
        }
    }
}