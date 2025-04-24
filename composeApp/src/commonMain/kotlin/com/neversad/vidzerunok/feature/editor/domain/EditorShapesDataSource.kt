package com.neversad.vidzerunok.feature.editor.domain

import com.neversad.vidzerunok.feature.editor.model.ShapeData
import kotlinx.coroutines.flow.Flow

interface EditorShapesDataSource {

    fun getShapes(): List<ShapeData>

    fun setShapes(shapes: List<ShapeData>)

    fun mapShapes(mapper: (ShapeData) -> ShapeData)

    fun observeShapes(): Flow<List<ShapeData>>

    fun addShape(shape: ShapeData)

    fun clearShapes()


}