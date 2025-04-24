package com.neversad.vidzerunok.feature.editor.shapes.deprecated

import androidx.compose.ui.graphics.drawscope.DrawScope

open class DragMode

object None : DragMode()
object Drag : DragMode()

interface ShapeState {
    val id: Long
    val isActive: Boolean

    fun withinActivationBounds(x: Float, y: Float): Boolean

    fun detectDragStart(x: Float, y: Float): DragMode

    fun toggleActive(
       isActive: Boolean,
    ): ShapeState

    fun applyDrag(mode: DragMode, deltaX: Float, deltaY: Float): ShapeState

    fun draw(drawScope: DrawScope)

}


