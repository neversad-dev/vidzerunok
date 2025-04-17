package com.neversad.vidzerunok.feature.gallery.domain

import com.neversad.vidzerunok.core.domain.EmptyResult
import com.neversad.vidzerunok.core.domain.VidzError
import kotlinx.coroutines.flow.Flow

interface ImageRepository {
    suspend fun saveFile(path: String): EmptyResult<VidzError>

    fun getAllFiles(): Flow<List<String>>

    suspend fun deleteImage(file: String): EmptyResult<VidzError>
}