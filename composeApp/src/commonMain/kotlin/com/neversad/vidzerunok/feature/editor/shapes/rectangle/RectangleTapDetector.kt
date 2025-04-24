package com.neversad.vidzerunok.feature.editor.shapes.rectangle

import com.neversad.vidzerunok.feature.editor.model.ShapeData
import com.neversad.vidzerunok.feature.editor.shapes.base.ShapeConstants.TOUCHABLE_WIDTH
import com.neversad.vidzerunok.feature.editor.shapes.base.ShapeTapDetector

object RectangleTapDetector : ShapeTapDetector {
    override fun detectTap(shape: ShapeData, x: Float, y: Float): Boolean {
        return with(shape) {
            (x in (startX - TOUCHABLE_WIDTH / 2)..(endX + TOUCHABLE_WIDTH / 2) &&
                    y in (startY - TOUCHABLE_WIDTH / 2)..(endY + TOUCHABLE_WIDTH / 2)) &&
                    !(x in startX + TOUCHABLE_WIDTH / 2..(endX - TOUCHABLE_WIDTH / 2) &&
                            y in startY + TOUCHABLE_WIDTH / 2..(endY - TOUCHABLE_WIDTH / 2))
        }
    }
}