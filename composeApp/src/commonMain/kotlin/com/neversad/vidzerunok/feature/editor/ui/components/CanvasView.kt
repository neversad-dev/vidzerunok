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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import com.neversad.vidzerunok.feature.editor.ui.shapes.DragMode
import com.neversad.vidzerunok.feature.editor.ui.shapes.None
import com.neversad.vidzerunok.feature.editor.ui.shapes.ShapeState
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.coil.AsyncImage


@Composable
fun CanvasView(
    modifier: Modifier = Modifier,
    filePath: String,
    shapes: List<ShapeState>,
    onChangeSelection: (ShapeState?) -> Unit,
    onDrag: (DragMode, Float, Float) -> Unit
) {

    var dragMode by remember { mutableStateOf<DragMode>(None) }
    var dragFinished by remember { mutableStateOf(false) }


    fun activeShape(): ShapeState? = shapes.lastOrNull { it.isActive }

    AsyncImage(
        file = PlatformFile(filePath),
        contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = modifier
            .pointerInput(shapes) {
                detectTapGestures { offset ->
                    val selected = shapes.lastOrNull {
                        it.withinActivationBounds(offset.x, offset.y)
                    }
                    onChangeSelection(selected)
                }
            }
            .pointerInput(activeShape()?.id, dragFinished) {
                detectDragGestures(
                    onDragStart = { startPoint ->
                        dragMode = activeShape()?.detectDragStart(startPoint.x, startPoint.y)?: None

                    },
                    onDrag = { _, dragAmount ->

                        onDrag(dragMode, dragAmount.x, dragAmount.y)
                    },
                    onDragEnd = {
                        dragFinished = !dragFinished
                        dragMode = None
                    },
                    onDragCancel = {
                        dragFinished = !dragFinished
                        dragMode = None
                    }
                )
            }
            .drawWithContent {
                drawContent()
                shapes.forEach {
                    it.draw(this)
                }
            }
    )
}
