package com.neversad.vidzerunok.feature.editor.ui

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.neversad.vidzerunok.core.presentation.ui.VidzerunokTopBar
import com.neversad.vidzerunok.feature.editor.ui.components.CanvasControls
import com.neversad.vidzerunok.feature.editor.ui.components.CanvasView
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun EditorScreen(
    viewModel: EditorViewModel = koinViewModel(),
    onBackClicked: () -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    EditorScreen(
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
fun EditorScreen(
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
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CanvasView(
                modifier = Modifier.fillMaxWidth(),
                filePath = state.file,
                shapes = state.shapes,
                onTap = { x, y ->
                    onAction(EditorAction.OnTap(x, y))
                },
                onDragStart = { x, y ->
                    onAction(EditorAction.OnDragStart(x, y))
                },
                onDrag = { x, y ->
                    onAction(EditorAction.OnDrag(x, y))
                },
                onDragFinish = {
                    onAction(EditorAction.OnDragFinish)
                }
            )

            CanvasControls(
                onShapeSelected = { shapeType ->
                    onAction(EditorAction.OnAddShape(shapeType))
                },
                onClearCanvas = {
                    onAction(EditorAction.ClearCanvasClick)
                }
            )
        }
    }

}

