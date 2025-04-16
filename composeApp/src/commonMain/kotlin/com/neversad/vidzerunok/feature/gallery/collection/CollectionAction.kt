package com.neversad.vidzerunok.feature.gallery.collection

sealed interface CollectionAction {
    data class OnImageClicked(val file: String): CollectionAction
    data object OnEditModeClick: CollectionAction
    data object OnEditModeDismiss: CollectionAction
    data class OnImageDelete(val file: String): CollectionAction
    data object OnOpenFilePickerDialog: CollectionAction
    data class OnFileSelected(val path: String): CollectionAction
    data object OnFilePickerCanceled: CollectionAction
}