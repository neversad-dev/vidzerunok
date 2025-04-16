package com.neversad.vidzerunok.feature.gallery.collection

import com.neversad.vidzerunok.core.presentation.UiText

data class CollectionState(
    val files: List<String> = emptyList(),
    val errorMessage: UiText? = null,
    val isFilePickerDialogActive: Boolean = false,
    val isEditMode: Boolean = false
)
