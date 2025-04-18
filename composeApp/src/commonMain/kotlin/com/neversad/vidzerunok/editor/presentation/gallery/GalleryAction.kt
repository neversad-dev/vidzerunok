package com.neversad.vidzerunok.editor.presentation.gallery

sealed interface GalleryAction {
    data class OnImageSelected(val file: String): GalleryAction
    data class OnImageDelete(val file: String): GalleryAction
}