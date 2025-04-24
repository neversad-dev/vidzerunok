package com.neversad.vidzerunok.feature.editor.domain

class ClearShapesUseCase(
    private val editorShapesDataSource: EditorShapesDataSource
) {

    operator fun invoke() {
        editorShapesDataSource.clearShapes()
    }
}