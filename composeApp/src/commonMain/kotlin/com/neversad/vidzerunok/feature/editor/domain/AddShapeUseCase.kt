package com.neversad.vidzerunok.feature.editor.domain

import com.neversad.vidzerunok.feature.editor.model.ShapeData
import com.neversad.vidzerunok.feature.editor.model.ShapeType

import kotlin.random.Random

class AddShapeUseCase(
    private val generateUidUseCase: GenerateUidUseCase,
    private val editorShapesDataSource: EditorShapesDataSource
) {

    operator fun invoke(shapeType: ShapeType) {

        editorShapesDataSource.mapShapes {
            it.copy(isActive = false)
        }

        val randomOffset = { range: Int -> Random.nextInt(-range, range).toFloat() }
        val shape = ShapeData(
            id = generateUidUseCase(),
            shapeType = shapeType,
            isActive = true,
            startX = 50f + randomOffset(100),
            startY = 50f + randomOffset(100),
            endX = 550f + randomOffset(100),
            endY = 450f + randomOffset(100)
        )
        editorShapesDataSource.addShape(shape)
    }
}