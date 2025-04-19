package com.neversad.vidzerunok.feature.editor.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.neversad.vidzerunok.feature.editor.ui.navigation.Editor
import com.neversad.vidzerunok.feature.editor.ui.shapes.ArrowState
import com.neversad.vidzerunok.feature.editor.ui.shapes.DragMode
import com.neversad.vidzerunok.feature.editor.ui.shapes.OvalState
import com.neversad.vidzerunok.feature.editor.ui.shapes.RectangleState
import com.neversad.vidzerunok.feature.editor.ui.shapes.ShapeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

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
        when (action) {
            EditorAction.ClearCanvasClick -> clearShapes()
            EditorAction.OnCancelSelection -> cancelSelection()
            EditorAction.OnRectangleControlClick -> addShape(RectangleState())
            EditorAction.OnCircleControlClick -> addShape(OvalState())
            EditorAction.OnArrowControlClick -> addShape(ArrowState())
            is EditorAction.OnShapeDrag -> changeShape(action.mode, action.x, action.y)
            is EditorAction.OnShapeSelected -> selectShape(action.shape)
            else -> Unit
        }

    }

    private fun clearShapes() {
        _state.update {
            it.copy(
                shapes = emptyList(),
            )
        }
    }

    private fun cancelSelection() {
        _state.update {
            it.copy(
                shapes = it.shapes.map { shape ->
                    if (shape.isActive) {
                        shape.toggleActive(isActive = false)
                    } else shape
                })
        }
    }

    private fun addShape(shape: ShapeState) {
        _state.update {
            val shapes = it.shapes.map { shape ->
                if (shape.isActive) {
                    shape.toggleActive(isActive = false)
                } else shape
            }

            it.copy(
                shapes = shapes + shape
            )
        }
    }


    private fun selectShape(selectedShape: ShapeState) {
        _state.update {
            it.copy(
                shapes = it.shapes.map { shape ->
                    if (shape.id == selectedShape.id) {
                        println("+ $shape")
                        shape.toggleActive(isActive = true)
                    } else {
                        println("- $shape")
                        shape.toggleActive(isActive = false)
                    }
                }
            )
        }
    }

    private fun changeShape(mode: DragMode, deltaX: Float, deltaY: Float) {
        _state.update {

            it.copy(
                shapes = it.shapes.map { shape ->
                    if (shape.isActive) {
                        shape.applyDrag(mode, deltaX, deltaY)
                    } else shape
                })
        }
    }


}