package com.neversad.vidzerunok.feature.editor.shapes.common.resizable

import com.neversad.vidzerunok.feature.editor.model.ShapeData
import com.neversad.vidzerunok.feature.editor.shapes.ShapeDragInteractor
import com.neversad.vidzerunok.feature.editor.shapes.common.ShapeConstants.TOUCHABLE_WIDTH

object LeftResizableInteractor : ShapeDragInteractor {

    override fun detectDrag(shape: ShapeData, x: Float, y: Float): Boolean {
        return with(shape) {
            val centerY = startY + (endY- startY) / 2
            x in (startX - TOUCHABLE_WIDTH / 2..startX + TOUCHABLE_WIDTH / 2) &&
                    y in (centerY- TOUCHABLE_WIDTH / 2..centerY + TOUCHABLE_WIDTH / 2)
        }
    }

    override fun applyDrag(shape: ShapeData, x: Float, y: Float): ShapeData {
        return with(shape) {
            copy(
                startX = startX + x
            )
        }
    }

}