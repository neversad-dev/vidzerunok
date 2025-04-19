package com.neversad.vidzerunok.feature.editor.ui

import com.neversad.vidzerunok.feature.editor.ui.shapes.DragMode
import com.neversad.vidzerunok.feature.editor.ui.shapes.ShapeState


sealed interface EditorAction {
    data object OnBackClick : EditorAction
    data object OnRectangleControlClick : EditorAction
    data object OnCircleControlClick : EditorAction
    data object OnArrowControlClick : EditorAction
    data class OnShapeSelected(val shape: ShapeState): EditorAction
    class OnShapeDrag(val mode: DragMode, val x: Float, val y: Float) : EditorAction

    data object OnCancelSelection: EditorAction
    data object ClearCanvasClick : EditorAction

}

