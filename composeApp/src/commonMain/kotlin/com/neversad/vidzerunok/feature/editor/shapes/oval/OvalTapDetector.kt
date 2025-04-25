package com.neversad.vidzerunok.feature.editor.shapes.oval

import com.neversad.vidzerunok.feature.editor.model.ShapeData
import com.neversad.vidzerunok.feature.editor.shapes.ShapeConstants.TOUCHABLE_WIDTH
import com.neversad.vidzerunok.feature.editor.shapes.ShapeTapDetector

object OvalTapDetector : ShapeTapDetector {
    override fun detectTap(shape: ShapeData, x: Float, y: Float): Boolean = with(shape) {
        val width = endX - startX
        val height = endY - startY
        val cx = startX + width / 2 // center X of the oval
        val cy = startY + height / 2 // center Y of the oval
        val rx = width / 2 + TOUCHABLE_WIDTH / 2 // horizontal radius with padding
        val ry = height / 2 + TOUCHABLE_WIDTH / 2 // vertical radius with padding

        // Check if the point (x, y) is within the outer bounds of the oval
        val withinOuterBounds =
            ((x - cx) * (x - cx)) / (rx * rx) + ((y - cy) * (y - cy)) / (ry * ry) <= 1

        // Check if the point (x, y) is within the inner bounds (excluding the padding)
        val rxInner = width / 2 - TOUCHABLE_WIDTH / 2 // inner horizontal radius
        val ryInner = height / 2 - TOUCHABLE_WIDTH / 2 // inner vertical radius

        val withinInnerBounds =
            ((x - cx) * (x - cx)) / (rxInner * rxInner) + ((y - cy) * (y - cy)) / (ryInner * ryInner) <= 1

        // Return true if the point is within the outer bounds and outside the inner bounds
        return withinOuterBounds && !withinInnerBounds
    }

}