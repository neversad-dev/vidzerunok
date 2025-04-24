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
import com.neversad.vidzerunok.feature.editor.model.ShapeData
import com.neversad.vidzerunok.feature.editor.model.ShapeType
import com.neversad.vidzerunok.feature.editor.ui.shapes.DragMode
import com.neversad.vidzerunok.feature.editor.ui.shapes.None
import com.neversad.vidzerunok.feature.editor.ui.shapes.drawer.ArrowShapeDrawer
import com.neversad.vidzerunok.feature.editor.ui.shapes.drawer.OvalShapeDrawer
import com.neversad.vidzerunok.feature.editor.ui.shapes.drawer.RectangleShapeDrawer
import com.neversad.vidzerunok.feature.editor.ui.shapes.drawer.ResizableShapeDrawer
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.coil.AsyncImage


@Composable
fun CanvasView(
    modifier: Modifier = Modifier,
    filePath: String,
    shapes: List<ShapeData>,
    onTap: (Float, Float) -> Unit,
    onDragStart: (Float, Float) -> Unit,
    onDrag: (Float, Float) -> Unit,
    onDragFinish: () -> Unit,
) {

    var dragMode by remember { mutableStateOf<DragMode>(None) }
    var dragFinished by remember { mutableStateOf(false) }


    fun activeShape(): ShapeData? = shapes.lastOrNull { it.isActive }

    AsyncImage(
        file = PlatformFile(filePath),
        contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = modifier
            .pointerInput(shapes) {
                detectTapGestures { offset ->
                    onTap(offset.x, offset.y)
                }
            }
            .pointerInput(activeShape()?.id, dragFinished) {
                detectDragGestures(
                    onDragStart = { startPoint ->
                        onDragStart(startPoint.x, startPoint.y)
                    },
                    onDrag = { _, dragAmount ->
                        onDrag(dragAmount.x, dragAmount.y)
                    },
                    onDragEnd = {
                        dragFinished = !dragFinished
                        onDragFinish()
                    },
                    onDragCancel = {
                        dragFinished = !dragFinished
                        onDragFinish()
                    }
                )
            }
            .drawWithContent {
                drawContent()
                shapes.forEach {
                    val drawer = when(it.shapeType){
                        ShapeType.RECTANGLE -> ResizableShapeDrawer(RectangleShapeDrawer)
                        ShapeType.OVAL -> ResizableShapeDrawer(OvalShapeDrawer)
                        ShapeType.ARROW -> ArrowShapeDrawer
                    }
                    drawer.drawShape(this,  it)
                }
            }
    )
}
