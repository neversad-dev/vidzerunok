package com.neversad.vidzerunok.feature.editor.domain

import com.neversad.vidzerunok.feature.editor.model.ShapeData
import kotlinx.coroutines.flow.Flow

class ObserveShapesUseCase(
    private val editorShapesDataSource: EditorShapesDataSource
) {

    operator fun invoke(): Flow<List<ShapeData>> {
        return editorShapesDataSource.observeShapes()
    }
}