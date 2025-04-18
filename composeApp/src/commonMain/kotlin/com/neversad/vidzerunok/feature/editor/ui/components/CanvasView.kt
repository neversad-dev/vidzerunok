package com.neversad.vidzerunok.feature.editor.ui.components


import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import com.neversad.vidzerunok.feature.editor.ui.DragMode
import com.neversad.vidzerunok.feature.editor.ui.Shape
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.coil.AsyncImage

private const val stroke_width = 10f
private const val touchable_width = 20f
private const val control_point_radius = 10f
private const val control_point_stroke = 1f



@Composable
fun CanvasView(
    modifier: Modifier = Modifier,
    filePath: String,
    shapes: List<Shape>,
    activeShape: Shape? = null,
    onChangeSelection: (Shape?) -> Unit,
    onDrag: (DragMode, Float, Float) -> Unit
) {

    var dragMode by remember { mutableStateOf(DragMode.NONE) }
    var dragFinished by remember { mutableStateOf(false) }

    AsyncImage(
        file = PlatformFile(filePath),
        contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = modifier
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
            .drawWithContent {
                drawContent()
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

    )
}