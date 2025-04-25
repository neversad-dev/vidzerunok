package com.neversad.vidzerunok.feature.editor.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.neversad.vidzerunok.feature.editor.domain.AddShapeUseCase
import com.neversad.vidzerunok.feature.editor.domain.ClearShapesUseCase
import com.neversad.vidzerunok.feature.editor.domain.DetectTapUseCase
import com.neversad.vidzerunok.feature.editor.domain.DragFinishUseCase
import com.neversad.vidzerunok.feature.editor.domain.DragStartUseCase
import com.neversad.vidzerunok.feature.editor.domain.DragUseCase
import com.neversad.vidzerunok.feature.editor.domain.ObserveShapesUseCase
import com.neversad.vidzerunok.feature.editor.ui.navigation.Editor
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class EditorViewModel(
    private val getShapesUseCase: ObserveShapesUseCase,
    private val addShapeUseCase: AddShapeUseCase,
    private val clearShapesUseCase: ClearShapesUseCase,
    private val detectTapUseCase: DetectTapUseCase,
    private val dragStartUseCase: DragStartUseCase,
    private val dragUseCase: DragUseCase,
    private val dragFinishUseCase: DragFinishUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var observeShapesJob: Job? = null

    private val filePath = savedStateHandle.toRoute<Editor>().file

    private val _state = MutableStateFlow(
        EditorState(
            file = filePath
        )
    )
    val state = _state
        .onStart {
            observeShapes()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )


    fun onAction(action: EditorAction) {
        when (action) {
            EditorAction.OnBackClick -> clearShapesUseCase()
            is EditorAction.OnAddShape -> addShapeUseCase(action.shapeType)
            EditorAction.ClearCanvasClick -> clearShapesUseCase()
            is EditorAction.OnTap -> detectTapUseCase(action.x, action.y)
            is EditorAction.OnDragStart -> dragStartUseCase(action.x, action.y)
            is EditorAction.OnDrag -> dragUseCase(action.x, action.y)
            EditorAction.OnDragFinish -> dragFinishUseCase()
            else -> Unit
        }

    }

    private fun observeShapes() {
        observeShapesJob?.cancel()
        observeShapesJob = getShapesUseCase()
            .onEach { shapes ->
                _state.update {
                    it.copy(
                        shapes = shapes
                    )
                }
            }
            .launchIn(viewModelScope)
    }
}