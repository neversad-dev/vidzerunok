package com.neversad.vidzerunok.feature.editor.shapes.arrow

import com.neversad.vidzerunok.feature.editor.model.ShapeData
import com.neversad.vidzerunok.feature.editor.shapes.ShapeConstants.TOUCHABLE_WIDTH
import com.neversad.vidzerunok.feature.editor.shapes.ShapeTapDetector
import kotlin.math.sqrt

object ArrowTapDetector : ShapeTapDetector {

    override fun detectTap(shape: ShapeData, x: Float, y: Float): Boolean = with(shape) {

        // Calculate distance from point to line segment
        val lineLength = sqrt(
            (endX - startX) * (endX - startX) + (endY - startY) * (endY - startY)
        )
        if (lineLength == 0f) return false

        // Calculate the projection of the point onto the line segment
        val t = maxOf(
            0f, minOf(
                1f,
                ((x - startX) * (endX - startX) + (y - startY) * (endY - startY)) / (lineLength * lineLength)
            )
        )

        val projectionX = startX + t * (endX - startX)
        val projectionY = startY + t * (endY - startY)

        // Check if the distance from point to its projection is within threshold
        val distance = kotlin.math.sqrt(
            (x - projectionX) * (x - projectionX) + (y - projectionY) * (y - projectionY)
        )

        return distance <= TOUCHABLE_WIDTH / 2
    }

}