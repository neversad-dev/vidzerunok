package com.neversad.vidzerunok.feature.editor.ui

import com.neversad.vidzerunok.feature.editor.ui.components.DragMode
import com.neversad.vidzerunok.feature.editor.ui.components.Rect

sealed interface EditorAction {
    data object OnBackClick : EditorAction
    data object OnRectangleControlClick : EditorAction
    data class OnShapeSelected(val rect: Rect): EditorAction
    class OnShapeDrag(val mode: DragMode, val x: Float, val y: Float) : EditorAction

    data object OnCancelSelection: EditorAction
    data object ClearCanvasClick : EditorAction

}

