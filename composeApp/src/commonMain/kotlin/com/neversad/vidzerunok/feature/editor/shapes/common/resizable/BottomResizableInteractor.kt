package com.neversad.vidzerunok.feature.editor.shapes.common.resizable

import com.neversad.vidzerunok.feature.editor.model.ShapeData
import com.neversad.vidzerunok.feature.editor.shapes.ShapeDragInteractor
import com.neversad.vidzerunok.feature.editor.shapes.common.ShapeConstants.TOUCHABLE_WIDTH

object BottomResizableInteractor : ShapeDragInteractor {

    override fun detectDrag(shape: ShapeData, x: Float, y: Float): Boolean {
        return with(shape) {
            val centerX = startX + (endX - startX) / 2
            x in (centerX - TOUCHABLE_WIDTH / 2..centerX + TOUCHABLE_WIDTH / 2) &&
                    y in (endY - TOUCHABLE_WIDTH / 2..endY + TOUCHABLE_WIDTH / 2)
        }
    }

    override fun applyDrag(shape: ShapeData, x: Float, y: Float): ShapeData {
        return with(shape) {
            copy(
                endY = endY + y
            )
        }
    }

}