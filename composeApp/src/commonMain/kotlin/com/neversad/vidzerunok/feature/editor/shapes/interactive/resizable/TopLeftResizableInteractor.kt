package com.neversad.vidzerunok.feature.editor.shapes.interactive.resizable

import com.neversad.vidzerunok.feature.editor.model.InteractionMode
import com.neversad.vidzerunok.feature.editor.model.ShapeData
import com.neversad.vidzerunok.feature.editor.shapes.ShapeInteractor
import com.neversad.vidzerunok.feature.editor.shapes.ShapeConstants.TOUCHABLE_WIDTH

object TopLeftResizableInteractor: ShapeInteractor {

    override fun detectInteraction(shape: ShapeData, x: Float, y: Float): ShapeData {
        return if (x in (shape.startX - TOUCHABLE_WIDTH/2 .. shape.startX + TOUCHABLE_WIDTH/2) &&
                y in (shape.startY - TOUCHABLE_WIDTH/2 .. shape.startY + TOUCHABLE_WIDTH/2)) {
            shape.copy(activeInteractionMode = InteractionMode.RESIZE_TL)
        } else {
            shape
        }
    }

    override fun applyInteraction(shape: ShapeData, x: Float, y: Float): ShapeData {
        return with(shape) {
            copy(
                startX = startX + x,
                startY = startY + y,
            )
        }
    }
}