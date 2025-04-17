package com.neversad.vidzerunok.core.data

import com.neversad.vidzerunok.feature.gallery.data.ImageDataSource
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.copyTo
import io.github.vinceglb.filekit.createDirectories
import io.github.vinceglb.filekit.delete
import io.github.vinceglb.filekit.div
import io.github.vinceglb.filekit.exists
import io.github.vinceglb.filekit.extension
import io.github.vinceglb.filekit.filesDir
import io.github.vinceglb.filekit.list
import io.github.vinceglb.filekit.path

private const val FOLDER_NAME = "images"

class InternalMemoryImageDataSource : ImageDataSource {

    override suspend fun getImages(): List<String> {
        val destinationFolder = FileKit.filesDir / FOLDER_NAME
        return destinationFolder.list().map { it.path }
    }

    override suspend fun copyImageToGallery(imagePath: String, fileName: String) {
        val sourceFile = imagePath.toPlatformFile()
        val destinationFolder = FileKit.filesDir / FOLDER_NAME
        val destinationFile =
            destinationFolder / "${fileName}.${sourceFile.extension}"

        if (!destinationFolder.exists()) {
            destinationFolder.createDirectories()
        }

        sourceFile.copyTo(destinationFile)
    }

    override suspend fun deleteImage(imagePath: String) {
        val destinationFile = PlatformFile(imagePath)
        destinationFile.delete()
    }
}