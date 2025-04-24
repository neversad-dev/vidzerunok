package com.neversad.vidzerunok.feature.editor.domain

import com.neversad.vidzerunok.feature.editor.shapes.base.ShapeTapDetector

class DetectTapUseCase(
    private val shapeTapDetector: ShapeTapDetector,
    private val editorShapesDataSource: EditorShapesDataSource
) {

    operator fun invoke(x: Float, y: Float) {

        val shapes =
            editorShapesDataSource.getShapes().map { it.copy(isActive = false) }.toMutableList()

        val tapped = shapes.lastOrNull {
            shapeTapDetector.detectTap(it, x, y)
        }
        if (tapped != null) {
            shapes.remove(tapped)
            shapes.add(tapped.copy(isActive = true))
        }

        editorShapesDataSource.setShapes(shapes)

    }
}