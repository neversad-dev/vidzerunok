package com.neversad.vidzerunok.feature.editor.domain

import com.neversad.vidzerunok.feature.editor.shapes.ShapeInteractor

class DragStartUseCase(
    private val editorShapesDataSource: EditorShapesDataSource,
    private val dragInteractor: ShapeInteractor
) {

    operator fun invoke(x: Float, y: Float) {
        editorShapesDataSource.mapShapes {
            if (it.isActive) {
                dragInteractor.detectInteraction(it, x, y)
            } else it
        }
    }


}