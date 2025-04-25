package com.neversad.vidzerunok.feature.editor.domain

import com.neversad.vidzerunok.feature.editor.shapes.ShapeInteractor

class DragFinishUseCase(
    private val dragInteractor: ShapeInteractor,
    private val editorShapesDataSource: EditorShapesDataSource
) {

    operator fun invoke() {
        editorShapesDataSource.mapShapes {
            if (it.isActive) {
                dragInteractor.finishInteraction(it)
            } else it
        }
    }
}