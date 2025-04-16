package com.neversad.vidzerunok.core.data

import com.neversad.vidzerunok.core.common.EmptyResult
import com.neversad.vidzerunok.core.common.Error
import com.neversad.vidzerunok.core.common.Result
import com.neversad.vidzerunok.core.domain.GenerateFileNameUseCase
import com.neversad.vidzerunok.core.domain.ImageRepository
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

private const val FOLDER_NAME = "images"

class InternalMemoryImageRepository(
    private val generateFileNameUseCase: GenerateFileNameUseCase
) : ImageRepository {

    private val updateEvent = MutableSharedFlow<Unit>(replay = 0)

    override suspend fun saveFile(path: String): EmptyResult<Error.Local> {

        return try {
            val sourceFile = path.toPlatformFile()
            val destinationFolder = FileKit.filesDir / FOLDER_NAME
            val destinationFile =
                destinationFolder / "${generateFileNameUseCase()}.${sourceFile.extension}"

            if (!destinationFolder.exists()) {
                destinationFolder.createDirectories()
            }

            sourceFile.copyTo(destinationFile)
            updateEvent.emit(Unit)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(Error.Local.UNKNOWN)
        }
    }

    override fun getAllFiles(): Flow<List<String>> {

        return updateEvent
            .onStart { emit(Unit) }
            .map {
                val destinationFolder = FileKit.filesDir / FOLDER_NAME
                destinationFolder.list().map { it.path }
            }
    }

    override suspend fun deleteImage(file: String): EmptyResult<Error.Local> {
        val destinationFile = PlatformFile(file)

        return try {
            destinationFile.delete()
            updateEvent.emit(Unit)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(Error.Local.UNKNOWN)
        }
    }
}