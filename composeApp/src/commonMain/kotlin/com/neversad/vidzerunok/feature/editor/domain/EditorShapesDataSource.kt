package com.neversad.vidzerunok.feature.editor.domain

import com.neversad.vidzerunok.feature.editor.model.ShapeData
import kotlinx.coroutines.flow.Flow

interface EditorShapesDataSource {

    fun getShapes(): Flow<List<ShapeData>>

    fun addShape(shape: ShapeData)

    fun clearShapes()


}