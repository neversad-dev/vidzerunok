package com.neversad.vidzerunok.feature.editor.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.neversad.vidzerunok.feature.editor.ui.navigation.Editor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.datetime.Clock
import kotlin.random.Random

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
            EditorAction.OnRectangleControlClick -> addShape()
            is EditorAction.OnShapeDrag -> changeShape(action.mode, action.x, action.y)
            is EditorAction.OnShapeSelected -> selectShape(action.shape)
            else -> Unit
        }
    }

    private fun clearShapes() {
        _state.update {
            it.copy(
                shapes = emptyList(),
                activeShape = null
            )
        }
    }

    private fun cancelSelection() {
        _state.update {
            val shapes = if(it.activeShape != null) {
                state.value.shapes + it.activeShape
            } else {
                state.value.shapes
            }
            it.copy(
                shapes = shapes   ,
                activeShape = null
            )
        }
    }

    private fun addShape() {
        _state.update {
            val shapes = if(it.activeShape != null) {
                state.value.shapes + it.activeShape
            } else {
                state.value.shapes
            }
            val randomX = Random.nextFloat() * 50f
            val randomY = Random.nextFloat() * 50f
            it.copy(
                shapes = shapes,
                activeShape = Shape(
                    Clock.System.now().epochSeconds,
                    randomX,
                    randomY,
                    300f, //+ randomX,
                    200f //+ randomY
                )
            )
        }
    }

    private fun selectShape(shape: Shape) {
        _state.update {

            val previousActiveShape = _state.value.activeShape
            val shapesWithoutActive = _state.value.shapes.filterNot { item   -> item == shape}
            val shapes = if(previousActiveShape != null) {
                shapesWithoutActive + previousActiveShape
            } else { shapesWithoutActive }
            it.copy(
                shapes = shapes,
                activeShape = shape
            )
        }
    }

    private fun changeShape(mode: DragMode, deltaX: Float, deltaY: Float) {
        _state.update {

            val newShape = it.activeShape?.run{
                return@run when (mode) {
                    DragMode.DRAG -> {
                        copy(
                            x = x + deltaX,
                            y = y + deltaY
                        )
                    }
                    DragMode.RESIZE_TOP_LEFT -> {
                        copy(
                            x = x + deltaX,
                            y = y + deltaY,
                            width = width - deltaX,
                            height = height - deltaY
                        )
                    }

                    DragMode.RESIZE_TOP -> {
                        copy(
                            y = y + deltaY,
                            height = height - deltaY
                        )
                    }

                    DragMode.RESIZE_TOP_RIGHT -> {
                        copy(
                            y = y + deltaY,
                            width = width + deltaX,
                            height = height - deltaY
                        )
                    }

                    DragMode.RESIZE_RIGHT -> {
                        copy(
                            width = width+deltaX
                        )
                    }

                    DragMode.RESIZE_BOTTOM_RIGHT -> {
                        copy(
                            width = width + deltaX,
                            height = height + deltaY
                        )
                    }

                    DragMode.RESIZE_BOTTOM -> {
                        copy(
                            height = height + deltaY
                        )
                    }

                    DragMode.RESIZE_BOTTOM_LEFT -> {
                        copy(
                            x = x + deltaX,
                            width = width - deltaX,
                            height = height + deltaY
                        )
                    }

                    DragMode.RESIZE_LEFT -> {
                        copy(
                            x = x + deltaX,
                            width = width - deltaX
                        )
                    }
                    else -> this
                }
            }
            it.copy(
                activeShape = newShape
            )
        }
    }


}