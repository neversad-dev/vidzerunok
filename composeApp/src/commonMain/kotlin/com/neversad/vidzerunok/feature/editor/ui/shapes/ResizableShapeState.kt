package com.neversad.vidzerunok.feature.editor.ui.shapes

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope

private object ResizeTopLeft : DragMode()
private object ResizeTop : DragMode()
private object ResizeTopRight : DragMode()
private object ResizeRight : DragMode()
private object ResizeBottomRight : DragMode()
private object ResizeBottom : DragMode()
private object ResizeBottomLeft : DragMode()
private object ResizeLeft : DragMode()

private const val touchable_width = 20f
private const val control_point_radius = 10f
private const val control_point_stroke = 1f

abstract class ResizableShapeState : ShapeState {

    abstract val x: Float
    abstract val y: Float
    abstract val width: Float
    abstract val height: Float

    private val topLeft: Offset get() = Offset(x, y)

    private val topRight: Offset get() = Offset(x + width, y)

    private val bottomLeft: Offset get() = Offset(x, y + height)

    private val bottomRight: Offset get() = Offset(x + width, y + height)

    private val top: Offset get() = Offset(x + width / 2, y)

    private val bottom: Offset get() = Offset(x + width / 2, y + height)

    private val left: Offset get() = Offset(x, y + height / 2)

    private val right: Offset get() = Offset(x + width, y + height / 2)

    private val controlPoints: List<Offset>
        get() = listOf(
            topLeft,
            top,
            topRight,
            left,
            bottomRight,
            bottom,
            bottomLeft,
            right
        )

    override fun detectDragStart(x: Float, y: Float): DragMode {
        val withinTopLeft =
            x in (this.topLeft.x - touchable_width * 2)..(this.topLeft.x + touchable_width * 2) &&
                    y in (this.topLeft.y - touchable_width * 2)..(this.topLeft.y + touchable_width * 2)

        val withinTop =
            x in (this.top.x - touchable_width * 2)..(this.top.x + touchable_width * 2) &&
                    y in (this.top.y - touchable_width * 2)..(this.top.y + touchable_width * 2)

        val withinTopRight =
            x in (this.topRight.x - touchable_width * 2)..(this.topRight.x + touchable_width * 2) &&
                    y in (this.topRight.y - touchable_width * 2)..(this.topRight.y + touchable_width * 2)

        val withinRight =
            x in (this.right.x - touchable_width * 2)..(this.right.x + touchable_width * 2) &&
                    y in (this.right.y - touchable_width * 2)..(this.right.y + touchable_width * 2)

        val withinBottomRight =
            x in (this.bottomRight.x - touchable_width * 2)..(this.bottomRight.x + touchable_width * 2) &&
                    y in (this.bottomRight.y - touchable_width * 2)..(this.bottomRight.y + touchable_width * 2)

        val withinBottom =
            x in (this.bottom.x - touchable_width * 2)..(this.bottom.x + touchable_width * 2) &&
                    y in (this.bottom.y - touchable_width * 2)..(this.bottom.y + touchable_width * 2)

        val withinBottomLeft =
            x in (this.bottomLeft.x - touchable_width * 2)..(this.bottomLeft.x + touchable_width * 2) &&
                    y in (this.bottomLeft.y - touchable_width * 2)..(this.bottomLeft.y + touchable_width * 2)

        val withinLeft =
            x in (this.left.x - touchable_width * 2)..(this.left.x + touchable_width * 2) &&
                    y in (this.left.y - touchable_width * 2)..(this.left.y + touchable_width * 2)

        return when {
            withinTopLeft -> ResizeTopLeft
            withinTopRight -> ResizeTopRight
            withinBottomRight -> ResizeBottomRight
            withinBottomLeft -> ResizeBottomLeft
            withinTop -> ResizeTop
            withinRight -> ResizeRight
            withinBottom -> ResizeBottom
            withinLeft -> ResizeLeft
            else -> Drag
        }
    }

    protected abstract fun copy(
        newX: Float,
        newY: Float,
        newWidth: Float,
        newHeight: Float
    ): ShapeState


    override fun applyDrag(mode: DragMode, deltaX: Float, deltaY: Float): ShapeState {
        var newX = x
        var newY = y
        var newWidth = width
        var newHeight = height

        when (mode) {
            Drag -> {
                newX = x + deltaX
                newY = y + deltaY
                newWidth = width
            }

            ResizeTopLeft -> {
                newX = x + deltaX
                newY = y + deltaY
                newWidth = width - deltaX
                newHeight = height - deltaY
            }

            ResizeTop -> {
                newY = y + deltaY
                newHeight = height - deltaY
            }

            ResizeTopRight -> {
                newY = y + deltaY
                newWidth = width + deltaX
                newHeight = height - deltaY
            }

            ResizeRight -> {
                newWidth = width + deltaX
            }

            ResizeBottomRight -> {
                newWidth = width + deltaX
                newHeight = height + deltaY
            }

            ResizeBottom -> {
                newHeight = height + deltaY
            }

            ResizeBottomLeft -> {
                newX = x + deltaX
                newWidth = width - deltaX
                newHeight = height + deltaY
            }

            ResizeLeft -> {
                newX = x + deltaX
                newWidth = width - deltaX
            }

        }

        return copy(newX, newY, newWidth, newHeight)
    }

    final override fun draw(drawScope: DrawScope) {
        onDrawShape(drawScope)
        if (isActive) {
            controlPoints.forEach { offset ->
                // Draw black outline
                drawScope.drawCircle(
                    color = Color.Black,
                    radius = control_point_radius + control_point_stroke * 2,
                    center = offset
                )
                drawScope.drawCircle(
                    color = Color.White,
                    radius = control_point_radius,
                    center = offset
                )
            }
        }
    }

    abstract fun onDrawShape(drawScope: DrawScope)

}