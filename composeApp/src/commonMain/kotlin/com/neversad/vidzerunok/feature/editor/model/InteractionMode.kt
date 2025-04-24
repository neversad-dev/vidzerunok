package com.neversad.vidzerunok.feature.editor.model

sealed class InteractionMode {
    data object None : InteractionMode()
    data object Drag : InteractionMode()
}