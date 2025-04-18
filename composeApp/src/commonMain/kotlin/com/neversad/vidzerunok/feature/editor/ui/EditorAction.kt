package com.neversad.vidzerunok.feature.editor.ui


sealed interface EditorAction {
    data object OnBackClick : EditorAction
    data object OnRectangleControlClick : EditorAction
    data class OnShapeSelected(val shape: Shape): EditorAction
    class OnShapeDrag(val mode: DragMode, val x: Float, val y: Float) : EditorAction

    data object OnCancelSelection: EditorAction
    data object ClearCanvasClick : EditorAction

}

