package com.neversad.vidzerunok.editor.domain

import com.neversad.vidzerunok.core.domain.EmptyResult
import com.neversad.vidzerunok.core.domain.Error

interface ImageRepository {
    suspend fun saveFile(path: String): EmptyResult<Error.Local>

    suspend fun getAllFiles(): List<String>
}