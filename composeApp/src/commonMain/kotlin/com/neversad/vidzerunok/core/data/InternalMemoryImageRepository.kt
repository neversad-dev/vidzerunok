package com.neversad.vidzerunok.core.data

import com.neversad.vidzerunok.core.common.EmptyResult
import com.neversad.vidzerunok.core.common.Error
import com.neversad.vidzerunok.core.domain.GenerateFileNameUseCase
import com.neversad.vidzerunok.core.domain.ImageRepository
import com.neversad.vidzerunok.core.common.Result
import io.github.vinceglb.filekit.FileKit
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

class InternalMemoryImageRepository(
    private val generateFileNameUseCase: GenerateFileNameUseCase
): ImageRepository {

    override suspend fun saveFile(path: String): EmptyResult<Error.Local> {

        return try {
            val sourceFile = path.toPlatformFile()
            val destinationFolder  = FileKit.filesDir / FOLDER_NAME
            val destinationFile =
                destinationFolder / "${generateFileNameUseCase()}.${sourceFile.extension}"

           if (!destinationFolder.exists()) {
               destinationFolder.createDirectories()
           }

            sourceFile.copyTo(destinationFile)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(Error.Local.UNKNOWN)
        }
    }

    override suspend fun getAllFiles(): List<String> {
        val destinationFolder  = FileKit.filesDir / FOLDER_NAME

        return destinationFolder.list()
            .map { it.path }
    }

    override suspend fun deleteImage(file: String): EmptyResult<Error.Local> {
        val destinationFile = file.toPlatformFile()

        return try {
            destinationFile.delete()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(Error.Local.UNKNOWN)
        }
    }
}