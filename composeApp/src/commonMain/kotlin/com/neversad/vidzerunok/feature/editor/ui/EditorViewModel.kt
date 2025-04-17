package com.neversad.vidzerunok.feature.editor.ui

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.neversad.vidzerunok.feature.editor.ui.navigation.Editor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.datetime.Clock

class EditorViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val filePath = savedStateHandle.toRoute<Editor>().file

    private val _state = MutableStateFlow(
        EditorState(
            file = filePath
        )
    )
    val state = _state.asStateFlow()


    fun onAction(action: EditorAction) {
        when(action) {
            EditorAction.ClearCanvasClick -> onClearCanvasClick()
            is EditorAction.OnDraw -> onDraw(action.offset)
            EditorAction.OnNewPathStart -> onNewPathStart()
            EditorAction.OnPathEnd -> onPathEnd()
            is EditorAction.OnSelectColor -> onSelectColor(action.color)
            else -> Unit
        }
    }


    private fun onClearCanvasClick() {
        _state.update {
            it.copy(
                currentPath = null,
                paths = emptyList()
            )
        }
    }

    private fun onDraw(offset: Offset) {
        val currentPathData = state.value.currentPath ?: return
        _state.update {
            it.copy(
                currentPath = currentPathData.copy(
                    path = currentPathData.path + offset
                )
            )
        }
    }

    private fun onPathEnd() {
        val currentPathData = state.value.currentPath ?: return
        _state.update {
            it.copy(
                currentPath = null,
                paths =  it.paths + currentPathData
            )
        }
    }

    private fun onSelectColor(color: Color) {
        _state.update { it.copy(selectedColor = color) }
    }

    private fun onNewPathStart() {
        _state.update {
            it.copy(
                currentPath = PathData(
                    id = Clock.System.now().toString(),
                    color = it.selectedColor,
                    path = emptyList()
                )
            )
        }
    }

}