package com.neversad.vidzerunok.feature.editor.ui

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

sealed interface EditorAction {
    data object OnBackClick : EditorAction
    data object OnNewPathStart : EditorAction
    data class OnDraw(val offset: Offset) : EditorAction
    data object OnPathEnd : EditorAction
    data class OnSelectColor(val color: Color) : EditorAction
    data object ClearCanvasClick : EditorAction

}

