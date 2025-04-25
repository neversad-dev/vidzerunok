package com.neversad.vidzerunok.feature.editor.data

import com.neversad.vidzerunok.feature.editor.domain.EditorShapesDataSource
import com.neversad.vidzerunok.feature.editor.model.ShapeData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class InMemoryEditorShapesDataSource : EditorShapesDataSource {
    private val shapes: MutableStateFlow<List<ShapeData>> = MutableStateFlow(emptyList())

    override fun getShapes(): List<ShapeData> {
        return shapes.value
    }

    override fun setShapes(shapes: List<ShapeData>) {
        this.shapes.value = shapes
    }

    override fun mapShapes(mapper: (ShapeData) -> ShapeData) {
        shapes.value = shapes.value.map(mapper)
    }

    override fun observeShapes(): Flow<List<ShapeData>> {
        return shapes.asStateFlow()
    }

    override fun addShape(shape: ShapeData) {
        shapes.value += shape
    }

    override fun clearShapes() {
        shapes.value = emptyList()
    }

    override fun getActiveShape(): ShapeData? {
        return shapes.value.lastOrNull { it.isActive }
    }
}