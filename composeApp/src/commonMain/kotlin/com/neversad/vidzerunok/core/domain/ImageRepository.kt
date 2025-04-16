package com.neversad.vidzerunok.core.domain

import com.neversad.vidzerunok.core.common.EmptyResult
import com.neversad.vidzerunok.core.common.Error
import kotlinx.coroutines.flow.Flow

interface ImageRepository {
    suspend fun saveFile(path: String): EmptyResult<Error.Local>

    fun getAllFiles(): Flow<List<String>>

    suspend fun deleteImage(file: String): EmptyResult<Error.Local>
}