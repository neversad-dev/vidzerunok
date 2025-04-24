package com.neversad.vidzerunok.feature.editor.domain

import com.neversad.vidzerunok.feature.editor.model.ShapeData
import com.neversad.vidzerunok.feature.editor.model.ShapeType

class AddShapeUseCase(
    private val generateUidUseCase: GenerateUidUseCase,
    private val editorShapesDataSource: EditorShapesDataSource
) {

    operator fun invoke(shapeType: ShapeType) {
        val shape = ShapeData(
            id = generateUidUseCase(),
            shapeType = shapeType,
            isActive = true,
            startX = 50f,
            startY = 50f,
            endX = 550f,
            endY = 450f
        )
        editorShapesDataSource.addShape(shape)
    }
}