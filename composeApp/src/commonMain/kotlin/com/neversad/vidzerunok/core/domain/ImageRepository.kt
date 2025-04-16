package com.neversad.vidzerunok.core.domain

import com.neversad.vidzerunok.core.common.EmptyResult
import com.neversad.vidzerunok.core.common.Error

interface ImageRepository {
    suspend fun saveFile(path: String): EmptyResult<Error.Local>

    suspend fun getAllFiles(): List<String>

    suspend fun deleteImage(file: String): EmptyResult<Error.Local>
}