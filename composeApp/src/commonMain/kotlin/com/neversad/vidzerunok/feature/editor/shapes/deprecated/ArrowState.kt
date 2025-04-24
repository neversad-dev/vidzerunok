package com.neversad.vidzerunok.feature.editor.shapes.deprecated

import androidx.compose.ui.graphics.drawscope.DrawScope
import com.neversad.vidzerunok.feature.editor.shapes.base.ShapeConstants.CONTROL_POINT_RADIUS
import com.neversad.vidzerunok.feature.editor.shapes.base.ShapeConstants.TOUCHABLE_WIDTH
import kotlinx.datetime.Clock
import kotlin.math.sqrt


private object MoveStart : DragMode()
private object MoveEnd : DragMode()


data class ArrowState(
    override val id: Long = Clock.System.now().toEpochMilliseconds(),
    override val isActive: Boolean = true,
    val startX: Float = 100f,
    val startY: Float = 100f,
    val endX: Float = 300f,
    val endY: Float = 300f,
) : ShapeState {


    override fun withinActivationBounds(x: Float, y: Float): Boolean {
       return true
    }

    override fun detectDragStart(x: Float, y: Float): DragMode {
        // Check if click is near the start point
        if (isNearPoint(x, y, startX, startY)) {
            return MoveStart
        }
        // Check if click is near the end point
        if (isNearPoint(x, y, endX, endY)) {
            return MoveEnd
        }
        return Drag
    }

    private fun isNearPoint(x: Float, y: Float, pointX: Float, pointY: Float): Boolean {
        val distance = sqrt(
            (x - pointX) * (x - pointX) + (y - pointY) * (y - pointY)
        )
        return distance <= (CONTROL_POINT_RADIUS + TOUCHABLE_WIDTH) * 2
    }

    override fun toggleActive(isActive: Boolean) = copy(isActive = isActive)

    override fun applyDrag(mode: DragMode, deltaX: Float, deltaY: Float): ShapeState {
        return when (mode) {
            Drag -> {
                copy(
                    startX = startX + deltaX,
                    startY = startY + deltaY,
                    endX = endX + deltaX,
                    endY = endY + deltaY
                )
            }

            MoveStart -> {
                copy(
                    startX = startX + deltaX,
                    startY = startY + deltaY,
                )
            }

            MoveEnd -> {
                copy(
                    endX = endX + deltaX,
                    endY = endY + deltaY
                )
            }

            else -> this
        }
    }

    override fun draw(drawScope: DrawScope) {

    }
}