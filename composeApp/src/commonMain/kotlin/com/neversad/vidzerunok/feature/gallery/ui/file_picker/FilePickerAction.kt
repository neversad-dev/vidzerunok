package com.neversad.vidzerunok.feature.gallery.ui.file_picker

sealed interface FilePickerAction {
    data object OnClearSelection: FilePickerAction
    data object OnSaveImageToGallery: FilePickerAction
    data object OnErrorMessageDismiss: FilePickerAction
}