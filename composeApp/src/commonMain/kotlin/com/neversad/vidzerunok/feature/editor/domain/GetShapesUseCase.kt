package com.neversad.vidzerunok.feature.editor.domain

import com.neversad.vidzerunok.feature.editor.model.ShapeData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetShapesUseCase(
    private val editorShapesDataSource: EditorShapesDataSource
) {

    operator fun invoke(): Flow<List<ShapeData>> {
        return editorShapesDataSource.getShapes()
    }
}