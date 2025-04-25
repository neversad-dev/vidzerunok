package com.neversad.vidzerunok.feature.editor.shapes.interactive.movable

import com.neversad.vidzerunok.feature.editor.model.InteractionMode
import com.neversad.vidzerunok.feature.editor.model.ShapeData
import com.neversad.vidzerunok.feature.editor.shapes.ShapeInteractor

object MovableShapeInteractor : ShapeInteractor {

    override fun detectInteraction(shape: ShapeData, x: Float, y: Float): ShapeData {
        return shape.copy(activeInteractionMode = InteractionMode.MOVE)
    }

    override fun applyInteraction(shape: ShapeData, x: Float, y: Float): ShapeData {
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