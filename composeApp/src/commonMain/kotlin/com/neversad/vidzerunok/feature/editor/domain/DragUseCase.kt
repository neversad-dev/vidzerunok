package com.neversad.vidzerunok.feature.editor.domain

import com.neversad.vidzerunok.feature.editor.shapes.ShapeInteractor

class DragUseCase(
    private val editorShapesDataSource: EditorShapesDataSource,
    private val dragInteractor: ShapeInteractor
) {

    operator fun invoke(x: Float, y: Float) {
        editorShapesDataSource.mapShapes {
            if (it.isActive) {
                dragInteractor.applyInteraction(it, x, y)
            } else it
        }
    }
}