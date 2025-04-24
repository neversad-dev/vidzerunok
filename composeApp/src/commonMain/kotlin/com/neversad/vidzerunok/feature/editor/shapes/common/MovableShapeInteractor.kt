package com.neversad.vidzerunok.feature.editor.shapes.common

import com.neversad.vidzerunok.feature.editor.model.ShapeData
import com.neversad.vidzerunok.feature.editor.shapes.ShapeDragInteractor

object MovableShapeInteractor : ShapeDragInteractor {

    override fun detectDrag(shape: ShapeData, x: Float, y: Float): Boolean {
        return true
    }

    override fun applyDrag(shape: ShapeData, x: Float, y: Float): ShapeData {
        return with(shape) {
            copy(
                startX = startX + x,
                startY = startY + y,
                endX = endX + x,
                endY = endY + y,
            )
        }
    }


}