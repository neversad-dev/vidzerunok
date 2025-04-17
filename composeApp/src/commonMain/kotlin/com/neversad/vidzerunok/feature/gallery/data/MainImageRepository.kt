package com.neversad.vidzerunok.feature.gallery.data

import com.neversad.vidzerunok.core.domain.EmptyResult
import com.neversad.vidzerunok.core.domain.Result
import com.neversad.vidzerunok.core.domain.VidzError
import com.neversad.vidzerunok.feature.gallery.domain.GenerateFileNameUseCase
import com.neversad.vidzerunok.feature.gallery.domain.ImageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart


class MainImageRepository(
    private val generateFileNameUseCase: GenerateFileNameUseCase,
    private val imageDataSource: ImageDataSource
) : ImageRepository {

    private val updateEvent = MutableSharedFlow<Unit>(replay = 0)

    override suspend fun saveFile(path: String): EmptyResult<VidzError> {

        return try {
           imageDataSource.copyImageToGallery(path, generateFileNameUseCase())
            updateEvent.emit(Unit)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(VidzError.UNKNOWN)
        }
    }

    override fun getAllFiles(): Flow<Result<List<String>, VidzError>> {

        return updateEvent
            .onStart { emit(Unit) }
            .map {
                try{
                   Result.Success(imageDataSource.getImages())
                } catch(e: Exception) {
                   Result.Error(VidzError.UNKNOWN)
                }
            }
    }

    override suspend fun deleteImage(file: String): EmptyResult<VidzError> {

        return try {
            imageDataSource.deleteImage(file)
            updateEvent.emit(Unit)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(VidzError.UNKNOWN)
        }
    }
}