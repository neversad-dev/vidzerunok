package com.neversad.vidzerunok.editor.presentation.file_picker

import com.neversad.vidzerunok.core.presentation.UiText

data class FilePickerState (
    val isFilePickerDialogActive: Boolean = false,
    val filePath: String? = null,
    val isLoading: Boolean = false,
    val errorMessage: UiText? = null,
    val isFileImported: Boolean = false
)