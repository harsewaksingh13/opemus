package com.oigma.opemus.data
import com.oigma.opemus.data.models.Track
import java.io.File
import java.io.FileFilter

/**
 * Created by Harsewak Singh on 14/01/2022.
 */

typealias LocalFile = File

fun LocalFile.toTrack() : Track {
    return Track(path, this)
}

interface FileManager {

    suspend fun findMediaFiles() : List<LocalFile>
}

/**
 * /sdcard
 * */
class FileManagerImpl(rootPath: String): FileManager {

    private val root: File = File(rootPath)

//    override suspend fun findMediaFiles(): List<LocalFile> {
//        val filesArray = root.listFiles()
//        return filesArray?.filterNotNull() ?: arrayListOf()
//    }

    override suspend fun findMediaFiles(): List<LocalFile> {
        var filesArray : MutableList<File> = arrayListOf()
        findMediaFile(root, filesArray)
        return filesArray?.filterNotNull() ?: arrayListOf()
    }

    private fun findMediaFile(file: File, files: MutableList<File>) {
        file.listFiles()?.forEach { file ->
            file?.let {
                if(it.isDirectory) {
                    findMediaFile(it, files)
                } else if (it.name.endsWith(".mp3")){
                     files.add(it)
                }
            }
        }
    }
}