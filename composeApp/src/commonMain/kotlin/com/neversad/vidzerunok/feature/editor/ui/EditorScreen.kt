package com.neversad.vidzerunok.feature.editor.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.neversad.vidzerunok.core.presentation.ui.VidzerunokTopBar
import com.neversad.vidzerunok.feature.editor.ui.components.CanvasControls
import com.neversad.vidzerunok.feature.editor.ui.components.DragMode
import com.neversad.vidzerunok.feature.editor.ui.components.Rect
import com.neversad.vidzerunok.feature.editor.ui.components.RectangleElement
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.coil.AsyncImage
import io.github.vinceglb.filekit.name
import kotlinx.datetime.Clock
import org.koin.compose.viewmodel.koinViewModel
import kotlin.random.Random

@Composable
fun ImageEditorScreen(
    viewModel: EditorViewModel = koinViewModel(),
    onBackClicked: () -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    val shapes by remember { mutableStateOf(mutableListOf<Rect>()) }
    var activeShape by remember { mutableStateOf<Rect?>(null) }

    ImageEditorScreen(
        state = state,
        shapes = shapes,
        activeShape = activeShape,
        onAction = { action ->
            when (action) {
                is EditorAction.OnBackClick -> {
                    onBackClicked()
                }

                is EditorAction.OnRectangleControlClick -> {
                    activeShape?.let { shapes.add(it) }

                    val randomX = Random.nextFloat() * 50f
                    val randomY = Random.nextFloat() * 50f
                    activeShape =
                        Rect(
                            Clock.System.now().epochSeconds,
                            randomX,
                            randomY,
                            200f + randomX,
                            300f + randomY
                        )
                }

                is EditorAction.OnShapeSelected -> {
                    activeShape?.let { shapes.add(it) }
                    shapes.remove(action.rect)
                    activeShape = action.rect
                }

                is EditorAction.OnCancelSelection -> {
                    activeShape?.let { shapes.add(it) }
                    activeShape = null
                }

                is EditorAction.ClearCanvasClick -> {
                    shapes.clear()
                    activeShape = null
                }
                is EditorAction.OnShapeDrag -> {

                    activeShape?.let {
                        when (action.mode) {
                            DragMode.NONE -> Unit
                            DragMode.DRAG -> {
                                activeShape = it.copy(
                                    x = it.x +action.x,
                                    y = it.y +action.y
                                )
                            }

                            DragMode.RESIZE_TOP_LEFT -> {
                                activeShape = it.copy(
                                    x = it.x + action.x,
                                    y = it.y + action.y,
                                    width = it.width - action.x,
                                    height = it.height - action.y
                                )
                            }

                            DragMode.RESIZE_TOP -> {
                                activeShape = it.copy(
                                    y = it.y + action.y,
                                    height = it.height - action.y
                                )
                            }

                            DragMode.RESIZE_TOP_RIGHT -> {
                                activeShape = it.copy(
                                    y = it.y + action.y,
                                    width = it.width + action.x,
                                    height = it.height - action.y
                                )
                            }

                            DragMode.RESIZE_RIGHT -> {
                                activeShape = it.copy(
                                    width = it.width + action.x
                                )
                            }

                            DragMode.RESIZE_BOTTOM_RIGHT -> {
                                activeShape = it.copy(
                                    width = it.width + action.x,
                                    height = it.height + action.y
                                )
                            }

                            DragMode.RESIZE_BOTTOM -> {
                                activeShape = it.copy(
                                    height = it.height + action.y
                                )
                            }

                            DragMode.RESIZE_BOTTOM_LEFT -> {
                                activeShape = it.copy(
                                    x = it.x + action.x,
                                    width = it.width - action.x,
                                    height = it.height + action.y
                                )
                            }

                            DragMode.RESIZE_LEFT -> {
                                activeShape = it.copy(
                                    x = it.x + action.x,
                                    width = it.width - action.x
                                )
                            }
                        }
                    }                }

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
    shapes: MutableList<Rect>,
    activeShape: Rect? = null,
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
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            EditorCanvas(
                modifier = Modifier.weight(1f),
                shapes = shapes,
                activeShape = activeShape,
                filePath = state.file,
                onAction = onAction,
            )

            CanvasControls(
                onRectangleClick = {
                    onAction(EditorAction.OnRectangleControlClick)
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
    shapes: List<Rect> = emptyList(),
    activeShape: Rect? = null,
    onAction: (EditorAction) -> Unit = {},
) {

    val file = PlatformFile(filePath)

    Box(
        modifier = modifier,
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxWidth(),
            file = file,
            contentDescription = file.name,
            contentScale = ContentScale.Fit,
        )
        RectangleElement(
            shapes = shapes,
            activeShape = activeShape,
            onChangeSelection = {
                if (it == null) {
                    onAction(EditorAction.OnCancelSelection)
                } else {
                    onAction(EditorAction.OnShapeSelected(it))
                }
            },
            onDrag = { mode, x, y ->
                onAction(EditorAction.OnShapeDrag(mode, x, y))
            }
        )
    }

}
