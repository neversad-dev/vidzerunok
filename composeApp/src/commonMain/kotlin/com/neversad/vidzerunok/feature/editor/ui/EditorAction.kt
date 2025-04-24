package com.neversad.vidzerunok.feature.editor.ui

import com.neversad.vidzerunok.feature.editor.model.ShapeType


sealed interface EditorAction {
    data object OnBackClick : EditorAction
    data class OnAddShape(val shapeType: ShapeType) : EditorAction
    data object ClearCanvasClick : EditorAction

    data class OnTap(val x: Float, val y: Float) : EditorAction
    data class OnDragStart(val x: Float, val y: Float) : EditorAction
    data class OnDrag(val x: Float, val y: Float) : EditorAction
    data object OnDragFinish : EditorAction
}

