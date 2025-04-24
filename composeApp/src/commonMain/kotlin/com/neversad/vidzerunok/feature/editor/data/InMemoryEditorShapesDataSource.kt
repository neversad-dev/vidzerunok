package com.neversad.vidzerunok.feature.editor.data

import com.neversad.vidzerunok.feature.editor.domain.EditorShapesDataSource
import com.neversad.vidzerunok.feature.editor.model.ShapeData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class InMemoryEditorShapesDataSource : EditorShapesDataSource {
    private val shapes: MutableStateFlow<List<ShapeData>> = MutableStateFlow(emptyList())

    override fun getShapes(): Flow<List<ShapeData>> {
        return shapes.asStateFlow()
    }

    override fun addShape(shape: ShapeData) {
        shapes.value += shape
    }

    override fun clearShapes() {
        shapes.value = emptyList()
    }
}