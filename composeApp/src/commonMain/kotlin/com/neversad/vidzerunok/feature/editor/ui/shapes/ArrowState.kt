package com.neversad.vidzerunok.feature.editor.ui.shapes

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import kotlinx.datetime.Clock


private object MoveStart : DragMode()
private object MoveEnd : DragMode()

private const val touchable_width = 20f  // Threshold for activation distance
private const val control_point_radius = 10f
private const val control_point_stroke = 1f

data class ArrowState(
    override val id: Long = Clock.System.now().toEpochMilliseconds(),
    override val isActive: Boolean = true,
    val startX: Float = 100f,
    val startY: Float = 100f,
    val endX: Float = 300f,
    val endY: Float = 300f,
) : ShapeState {


    override fun withinActivationBounds(x: Float, y: Float): Boolean {
        // First check if point is near start or end points
        if (isNearPoint(x, y, startX, startY) || isNearPoint(x, y, endX, endY)) {
            return true
        }

        // Calculate distance from point to line segment
        val lineLength = kotlin.math.sqrt(
            (endX - startX) * (endX - startX) + (endY - startY) * (endY - startY)
        )
        if (lineLength == 0f) return false

        // Calculate the projection of the point onto the line segment
        val t = maxOf(0f, minOf(1f,
            ((x - startX) * (endX - startX) + (y - startY) * (endY - startY)) / (lineLength * lineLength)
        ))

        val projectionX = startX + t * (endX - startX)
        val projectionY = startY + t * (endY - startY)

        // Check if the distance from point to its projection is within threshold
        val distance = kotlin.math.sqrt(
            (x - projectionX) * (x - projectionX) + (y - projectionY) * (y - projectionY)
        )

        return distance <= touchable_width
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
        val distance = kotlin.math.sqrt(
            (x - pointX) * (x - pointX) + (y - pointY) * (y - pointY)
        )
        return distance <= (control_point_radius + touchable_width) * 2
    }

    override fun toggleActive(isActive: Boolean) = copy(isActive = isActive)

    override fun applyDrag(mode: DragMode, deltaX: Float, deltaY: Float): ShapeState {
        return when (mode){
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
        with(drawScope) {

            if (isActive) {
                // Arrow start
                // Draw black outline
                drawCircle(
                    color = Color.Black,
                    radius = control_point_radius + control_point_stroke * 2,
                    center = Offset(startX, startY)
                )
                drawCircle(
                    color = Color.White,
                    radius = control_point_radius,
                    center = Offset(startX, startY)
                )

                // Arrow end
                // Draw black outline
                drawCircle(
                    color = Color.Black,
                    radius = control_point_radius + control_point_stroke * 2,
                    center = Offset(endX, endY)
                )
                drawCircle(
                    color = Color.White,
                    radius = control_point_radius,
                    center = Offset(endX, endY)
                )
            }
        }
    }
}