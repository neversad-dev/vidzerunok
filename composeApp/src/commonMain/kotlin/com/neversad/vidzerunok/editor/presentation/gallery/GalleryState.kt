package com.neversad.vidzerunok.editor.presentation.gallery

import com.neversad.vidzerunok.core.presentation.UiText

data class GalleryState(
    val files: List<String> = emptyList(),
    val errorMessage: UiText? = null
)
