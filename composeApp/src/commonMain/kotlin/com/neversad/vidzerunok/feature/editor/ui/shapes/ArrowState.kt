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
private const val touchable_width = 20f  // Threshold for activation distance
private const val control_point_radius = 10f
private const val control_point_stroke = 1f
private const val outline_width = 4f  // Width of the black outline

data class ArrowState(
    override val id: Long = Clock.System.now().epochSeconds,
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
            val start = Offset(startX, startY)  // Start point of the arrow
            val end = Offset(endX, endY)    // End point of the arrow

            // Calculate the angle of the arrow
            val angle = atan2((end.y - start.y).toDouble(), (end.x - start.x).toDouble())
            
            // Calculate the base points of the white triangle
            val whiteArrowheadLeft = Offset(
                end.x - arrow_head_size * cos(angle + PI / 6).toFloat(),
                end.y - arrow_head_size * sin(angle + PI / 6).toFloat()
            )

            val whiteArrowheadRight = Offset(
                end.x - arrow_head_size * cos(angle - PI / 6).toFloat(),
                end.y - arrow_head_size * sin(angle - PI / 6).toFloat()
            )

            // Calculate the center point of the triangle base (where the line should end)
            val lineEnd = Offset(
                (whiteArrowheadLeft.x + whiteArrowheadRight.x) / 2,
                (whiteArrowheadLeft.y + whiteArrowheadRight.y) / 2
            )

            // Calculate the black outline points by extending each white triangle point outward
            val blackArrowTip = Offset(
                end.x + outline_width * cos(angle).toFloat(),
                end.y + outline_width * sin(angle).toFloat()
            )


            
            val blackArrowheadLeft = Offset(
                whiteArrowheadLeft.x - outline_width * cos(angle + PI / 2).toFloat(),
                whiteArrowheadLeft.y - outline_width * sin(angle + PI / 2).toFloat()
            )

            val blackArrowheadRight = Offset(
                whiteArrowheadRight.x - outline_width * cos(angle - PI / 2).toFloat(),
                whiteArrowheadRight.y - outline_width * sin(angle - PI / 2).toFloat()
            )


            // Draw the black outline of the line
            drawLine(
                color = Color.Black,
                start = start,
                end = lineEnd,
                strokeWidth = stroke_width + outline_width
            )

            // Draw the white line
            drawLine(
                color = Color.White,
                start = start,
                end = lineEnd,
                strokeWidth = stroke_width
            )

            // Draw the black arrowhead outline
            val blackPath = Path().apply {
                moveTo(blackArrowTip.x, blackArrowTip.y)
                lineTo(blackArrowheadLeft.x, blackArrowheadLeft.y)
//                lineTo(blackBaseLeft.x, blackBaseLeft.y)
//                lineTo(blackBaseRight.x, blackBaseRight.y)
                lineTo(blackArrowheadRight.x, blackArrowheadRight.y)
                close()
            }

            drawPath(
                color = Color.Black,
                path = blackPath
            )

            // Draw the white arrowhead
            val whitePath = Path().apply {
                moveTo(end.x, end.y)
                lineTo(whiteArrowheadLeft.x, whiteArrowheadLeft.y)
                lineTo(whiteArrowheadRight.x, whiteArrowheadRight.y)
                close()
            }

            drawPath(
                color = Color.White,
                path = whitePath
            )

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