package com.neversad.vidzerunok.feature.editor.ui

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.neversad.vidzerunok.core.presentation.ui.VidzerunokTopBar
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.coil.AsyncImage
import io.github.vinceglb.filekit.name
import org.koin.compose.viewmodel.koinViewModel
import kotlin.math.abs

@Composable
fun ImageEditorScreen(
    viewModel: EditorViewModel = koinViewModel(),
    onBackClicked: () -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    ImageEditorScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is EditorAction.OnBackClick -> {
                    onBackClicked()
                }

                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageEditorScreen(
    state: EditorState,
    onAction: (EditorAction) -> Unit,
) {
    Scaffold(
        topBar = {
            VidzerunokTopBar(
                title = { Text("Edit image") },
                navigationIcon = {
                    IconButton(onClick = {
                        onAction(EditorAction.OnBackClick)
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(paddingValues),
        ) {

            EditorCanvas(
                modifier = Modifier.weight(1f),
                filePath = state.file,
                onAction = onAction,
                paths = state.paths,
                currentPath = state.currentPath
            )

            CanvasControls(
                selectedColor = state.selectedColor,
                colors = allColors,
                onSelectedColor = {
                    onAction(EditorAction.OnSelectColor(it))
                },
                onClearCanvas = {
                    onAction(EditorAction.ClearCanvasClick)
                }
            )
        }
    }

}

@Composable
fun EditorCanvas(
    modifier: Modifier = Modifier,
    filePath: String,
    onAction: (EditorAction) -> Unit = {},

    paths: List<PathData> = emptyList(),
    currentPath: PathData? = null,
) {

    val file = PlatformFile(filePath)

    AsyncImage(
        file = file,
        contentDescription = file.name,
        contentScale = ContentScale.Fit,
        modifier = modifier
            .sizeIn(maxWidth = 400.dp)
            .pointerInput(true) {
                detectDragGestures(
                    onDragStart = {
                        onAction(EditorAction.OnNewPathStart)
                    },
                    onDragEnd = {
                        onAction(EditorAction.OnPathEnd)
                    },
                    onDrag = { change, _ ->
                        onAction(EditorAction.OnDraw(change.position))
                    },
                    onDragCancel = {
                        onAction(EditorAction.OnPathEnd)
                    }
                )
            }
            .drawWithContent {
                drawContent()
                paths.forEach { pathData ->
                    drawPath(
                        path = pathData.path,
                        color = pathData.color,
                    )
                }
                currentPath?.let { pathData ->
                    drawPath(
                        path = pathData.path,
                        color = pathData.color,
                    )
                }
            }
    )
}

private fun DrawScope.drawPath(
    path: List<Offset>,
    color: Color,
    thickness: Float = 10f
) {
    val smoothedPath = Path().apply {
        if (path.isNotEmpty()) {
            moveTo(path.first().x, path.first().y)

            val smoothness = 5
            for (i in 1..path.lastIndex) {
                val from = path[i - 1]
                val to = path[i]
                val dx = abs(from.x - to.x)
                val dy = abs(from.y - to.y)
                if (dx >= smoothness || dy >= smoothness) {
                    quadraticTo(
                        x1 = (from.x + to.x) / 2,
                        y1 = (from.y + to.y) / 2,
                        x2 = to.x,
                        y2 = to.y
                    )
                }
            }
        }
    }
    drawPath(
        path = smoothedPath,
        color = color,
        style = Stroke(
            width = thickness,
            cap = StrokeCap.Round,
            join = StrokeJoin.Round
        )
    )
}