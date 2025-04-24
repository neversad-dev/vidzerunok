package com.neversad.vidzerunok.feature.editor.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.neversad.vidzerunok.feature.editor.domain.AddShapeUseCase
import com.neversad.vidzerunok.feature.editor.domain.ClearShapesUseCase
import com.neversad.vidzerunok.feature.editor.domain.GetShapesUseCase
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
    private val getShapesUseCase: GetShapesUseCase,
    private val addShapeUseCase: AddShapeUseCase,
    private val clearShapesUseCase: ClearShapesUseCase,
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