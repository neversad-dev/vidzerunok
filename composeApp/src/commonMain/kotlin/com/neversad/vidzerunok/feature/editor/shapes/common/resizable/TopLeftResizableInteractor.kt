package com.neversad.vidzerunok.feature.editor.shapes.common.resizable

import com.neversad.vidzerunok.feature.editor.model.ShapeData
import com.neversad.vidzerunok.feature.editor.shapes.ShapeDragInteractor
import com.neversad.vidzerunok.feature.editor.shapes.common.ShapeConstants.TOUCHABLE_WIDTH

object TopLeftResizableInteractor: ShapeDragInteractor{

    override fun detectDrag(shape: ShapeData, x: Float, y: Float): Boolean {
        return x in (shape.startX - TOUCHABLE_WIDTH/2 .. shape.startX + TOUCHABLE_WIDTH/2) &&
                y in (shape.startY - TOUCHABLE_WIDTH/2 .. shape.startY + TOUCHABLE_WIDTH/2)
    }

    override fun applyDrag(shape: ShapeData, x: Float, y: Float): ShapeData {
        return with(shape) {
            copy(
                startX = startX + x,
                startY = startY + y,
            )
        }
    }

}