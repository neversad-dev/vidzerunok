package com.neversad.vidzerunok.feature.editor.ui.components


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput

private const val stroke_width = 10f
private const val touchable_width = 20f
private const val control_point_radius = 10f
private const val control_point_stroke = 1f

data class Rect(
    val id: Long = 0,
    val x: Float = 50f,
    val y: Float = 50f,
    val width: Float = 200f,
    val height: Float = 300f,
) {

    val topLeft: Offset get() = Offset(x, y)

    val topRight: Offset get() = Offset(x + width, y)

    val bottomLeft: Offset get() = Offset(x, y + height)

    val bottomRight: Offset get() = Offset(x + width, y + height)

    val top: Offset get() = Offset(x + width / 2, y)

    val bottom: Offset get() = Offset(x + width / 2, y + height)

    val left: Offset get() = Offset(x, y + height / 2)

    val right: Offset get() = Offset(x + width, y + height / 2)

    val controlPoints: List<Offset> = listOf(
        topLeft,
        top,
        topRight,
        left,
        bottomRight,
        bottom,
        bottomLeft,
        right
    )


}

enum class DragMode {
    NONE,
    DRAG,
    RESIZE_TOP_LEFT,
    RESIZE_TOP,
    RESIZE_TOP_RIGHT,
    RESIZE_RIGHT,
    RESIZE_BOTTOM_RIGHT,
    RESIZE_BOTTOM,
    RESIZE_BOTTOM_LEFT,
    RESIZE_LEFT,
}

@Composable
fun RectangleElement(
    modifier: Modifier = Modifier,
    shapes: List<Rect>,
    activeShape: Rect? = null,
    onChangeSelection: (Rect?) -> Unit,
    onDrag: (DragMode, Float, Float) -> Unit
) {

    var dragMode by remember { mutableStateOf(DragMode.NONE) }
    var dragFinished by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(shapes) {
                detectTapGestures { offset ->
                    val selected = shapes.lastOrNull {
                        (offset.x in (it.x - stroke_width - touchable_width)..(it.x + it.width + stroke_width + touchable_width) &&
                                offset.y in (it.y - stroke_width - touchable_width)..(it.y + it.height + stroke_width + touchable_width)) &&
                                !(offset.x in it.x + touchable_width..(it.x + it.width - touchable_width) &&
                                        offset.y in it.y + touchable_width..(it.y + it.height - touchable_width))

                    }
                    onChangeSelection(selected)
                }
            }
            .pointerInput(activeShape?.id, dragFinished) {
                detectDragGestures(
                    onDragStart = { startPoint ->
                        activeShape?.let {
                            val withinTopLeft =
                                startPoint.x in (it.topLeft.x - touchable_width * 2)..(it.topLeft.x + touchable_width * 2) &&
                                        startPoint.y in (it.topLeft.y - touchable_width * 2)..(it.topLeft.y + touchable_width * 2)

                            val withinTop =
                                startPoint.x in (it.top.x - touchable_width * 2)..(it.top.x + touchable_width * 2) &&
                                        startPoint.y in (it.top.y - touchable_width * 2)..(it.top.y + touchable_width * 2)

                            val withinTopRight =
                                startPoint.x in (it.topRight.x - touchable_width * 2)..(it.topRight.x + touchable_width * 2) &&
                                        startPoint.y in (it.topRight.y - touchable_width * 2)..(it.topRight.y + touchable_width * 2)

                            val withinRight =
                                startPoint.x in (it.right.x - touchable_width * 2)..(it.right.x + touchable_width * 2) &&
                                        startPoint.y in (it.right.y - touchable_width * 2)..(it.right.y + touchable_width * 2)

                            val withinBottomRight =
                                startPoint.x in (it.bottomRight.x - touchable_width * 2)..(it.bottomRight.x + touchable_width * 2) &&
                                        startPoint.y in (it.bottomRight.y - touchable_width * 2)..(it.bottomRight.y + touchable_width * 2)

                            val withinBottom =
                                startPoint.x in (it.bottom.x - touchable_width * 2)..(it.bottom.x + touchable_width * 2) &&
                                        startPoint.y in (it.bottom.y - touchable_width * 2)..(it.bottom.y + touchable_width * 2)

                            val withinBottomLeft =
                                startPoint.x in (it.bottomLeft.x - touchable_width * 2)..(it.bottomLeft.x + touchable_width * 2) &&
                                        startPoint.y in (it.bottomLeft.y - touchable_width * 2)..(it.bottomLeft.y + touchable_width * 2)

                            val withinLeft =
                                startPoint.x in (it.left.x - touchable_width * 2)..(it.left.x + touchable_width * 2) &&
                                        startPoint.y in (it.left.y - touchable_width * 2)..(it.left.y + touchable_width * 2)

                            dragMode = when {
                                withinTopLeft -> DragMode.RESIZE_TOP_LEFT
                                withinTopRight -> DragMode.RESIZE_TOP_RIGHT
                                withinBottomRight -> DragMode.RESIZE_BOTTOM_RIGHT
                                withinBottomLeft -> DragMode.RESIZE_BOTTOM_LEFT
                                withinTop -> DragMode.RESIZE_TOP
                                withinRight -> DragMode.RESIZE_RIGHT
                                withinBottom -> DragMode.RESIZE_BOTTOM
                                withinLeft -> DragMode.RESIZE_LEFT
                                else -> DragMode.DRAG
                            }
                        }

                    },
                    onDrag = { _, dragAmount ->

                        onDrag(dragMode, dragAmount.x, dragAmount.y)
                    },
                    onDragEnd = {
                        dragFinished = !dragFinished
                        dragMode = DragMode.NONE },
                    onDragCancel = {
                        dragFinished = !dragFinished
                        dragMode = DragMode.NONE }
                )
            }

    ) {
        Canvas(
            modifier = Modifier
                .matchParentSize()
        ) {
            shapes.forEach {
                drawRect(
                    color = Color.Red,
                    topLeft = Offset(it.x, it.y),
                    size = Size(it.width, it.height),
                    style = Stroke(width = stroke_width)
                )
            }

            activeShape?.let {
                drawRect(
                    color = Color.Red,
                    topLeft = Offset(it.x, it.y),
                    size = Size(it.width, it.height),
                    style = Stroke(width = stroke_width)
                )


                it.controlPoints.forEach { offset ->
                    // Draw black outline
                    drawCircle(
                        color = Color.Black,
                        radius = control_point_radius + control_point_stroke * 2,
                        center = offset
                    )
                    drawCircle(
                        color = Color.White,
                        radius = control_point_radius,
                        center = offset
                    )
                }
            }

        }
    }
}