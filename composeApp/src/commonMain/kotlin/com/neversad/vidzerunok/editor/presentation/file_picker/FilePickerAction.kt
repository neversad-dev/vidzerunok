package com.neversad.vidzerunok.editor.presentation.file_picker

sealed interface FilePickerAction {
    data object OnOpenFilePickerDialog: FilePickerAction
    data class OnFileSelected(val path: String): FilePickerAction
    data object OnFilePickerCanceled: FilePickerAction
    data object OnClearSelection: FilePickerAction
    data object OnSaveImageToGallery: FilePickerAction
    data object OnErrorMessageDismiss: FilePickerAction
}