package com.neversad.vidzerunok.feature.gallery.file_picker

import com.neversad.vidzerunok.core.presentation.UiText

data class FilePickerState (
    val filePath: String? = null,
    val isLoading: Boolean = false,
    val errorMessage: UiText? = null,
    val isFileImported: Boolean = false
)