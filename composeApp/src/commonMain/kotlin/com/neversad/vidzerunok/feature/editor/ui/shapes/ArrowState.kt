package com.neversad.vidzerunok.feature.editor.ui.shapes

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import kotlinx.datetime.Clock
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

private object MoveStart : DragMode()
private object MoveEnd : DragMode()

private const val stroke_width = 10f
private const val arrow_head_size = 40f

data class ArrowState(
    override val id: Long = Clock.System.now().epochSeconds,
    override val isActive: Boolean = true,
    val startX: Float = 100f,
    val startY: Float = 100f,
    val endX: Float = 300f,
    val endY: Float = 300f,
) : ShapeState {

    override fun withinActivationBounds(x: Float, y: Float): Boolean {
        return false
    }

    override fun detectDragStart(x: Float, y: Float): DragMode {
        return None
    }

    override fun toggleActive(isActive: Boolean) = copy(isActive = isActive)

    override fun applyDrag(mode: DragMode, deltaX: Float, deltaY: Float): ShapeState {
        return this
    }

    override fun draw(drawScope: DrawScope) {
        with(drawScope) {
            val start = Offset(startX, startY)  // Start point of the arrow
            val end = Offset(endX, endY)    // End point of the arrow

            // Draw the line (shaft of the arrow)
            drawLine(
                color = Color.Black,
                start = start,
                end = end,
                strokeWidth = stroke_width
            )

            // Calculate the angle of the arrowhead
            val angle = atan2((end.y - start.y).toDouble(), (end.x - start.x).toDouble())

            // Calculate the positions for the two points of the arrowhead
            val arrowheadLeft = Offset(
                end.x - arrow_head_size * cos(angle + PI / 6).toFloat(),
                end.y - arrow_head_size * sin(angle + PI / 6).toFloat()
            )

            val arrowheadRight = Offset(
                end.x - arrow_head_size * cos(angle - PI / 6).toFloat(),
                end.y - arrow_head_size * sin(angle - PI / 6).toFloat()
            )

            // Draw the arrowhead (a triangle)
            val path = Path().apply {
                moveTo(end.x, end.y)  // Move to the arrow tail
                lineTo(arrowheadLeft.x, arrowheadLeft.y)  // Left point of the arrowhead
                lineTo(arrowheadRight.x, arrowheadRight.y) // Right point of the arrowhead
                close()  // Close the path to form the triangle
            }

            drawPath(
                color = Color.Black,
                path = path
            )
        }
    }
}