package com.neversad.vidzerunok.feature.editor.ui.components


import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import com.neversad.vidzerunok.feature.editor.model.ShapeData
import com.neversad.vidzerunok.feature.editor.shapes.ShapeDrawer
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.coil.AsyncImage
import org.koin.compose.koinInject


@Composable
fun CanvasView(
    modifier: Modifier = Modifier,
    filePath: String,
    shapes: List<ShapeData>,
    onTap: (Float, Float) -> Unit,
    onDragStart: (Float, Float) -> Unit,
    onDrag: (Float, Float) -> Unit,
    onDragFinish: () -> Unit,
    drawer: ShapeDrawer = koinInject()
) {

    AsyncImage(
        file = PlatformFile(filePath),
        contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = modifier
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    onTap(offset.x, offset.y)
                }
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { startPoint ->
                        onDragStart(startPoint.x, startPoint.y)
                    },
                    onDrag = { _, dragAmount ->
                        onDrag(dragAmount.x, dragAmount.y)
                    },
                    onDragEnd = {
                        onDragFinish()
                    },
                    onDragCancel = {
                        onDragFinish()
                    }
                )
            }
            .drawWithContent {
                drawContent()
                shapes.forEach {
                    drawer.drawShape(this, it)
                }
            }
    )
}
