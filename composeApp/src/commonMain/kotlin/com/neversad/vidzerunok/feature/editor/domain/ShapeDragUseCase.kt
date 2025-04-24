package com.neversad.vidzerunok.feature.editor.domain

import com.neversad.vidzerunok.feature.editor.model.InteractionMode
import com.neversad.vidzerunok.feature.editor.shapes.ShapeDragInteractor

class ShapeDragUseCase(
    private val editorShapesDataSource: EditorShapesDataSource,
    private val shapeDragInteractor: ShapeDragInteractor
) {

    private var interactionMode: InteractionMode = InteractionMode.None

    fun onDragStart(x: Float, y: Float) {
        val activeShape = editorShapesDataSource.getShapes().firstOrNull { it.isActive }

        if (activeShape != null) {
            if (shapeDragInteractor.detectDrag(activeShape, x, y)) {
                interactionMode = InteractionMode.Drag
            } else {
                interactionMode = InteractionMode.None
            }
        } else {
            interactionMode = InteractionMode.None
        }
    }

    fun onDrag(x: Float, y: Float) {
        if (interactionMode == InteractionMode.Drag) {
            editorShapesDataSource.mapShapes {
                if (it.isActive) {
                    shapeDragInteractor.applyDrag(it, x, y)
                } else {
                    it
                }
            }
        }
    }

    fun onDragFinish() {
        interactionMode = InteractionMode.None
    }
}