package com.neversad.vidzerunok.feature.editor.ui.shapes

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import kotlinx.datetime.Clock

private const val stroke_width = 10f
private const val touchable_width = 20f

data class OvalState(
    override val id: Long = Clock.System.now().toEpochMilliseconds(),
    override val isActive: Boolean = true,
    override val x: Float = 50f,
    override val y: Float = 50f,
    override val width: Float = 200f,
    override val height: Float = 300f,
) : ResizableShapeState() {


    override fun withinActivationBounds(x: Float, y: Float): Boolean {
        // Oval center and radius
        val cx = this.x + this.width / 2 // center X of the oval
        val cy = this.y + this.height / 2 // center Y of the oval
        val rx = this.width / 2 + stroke_width + touchable_width // horizontal radius with padding
        val ry = this.height / 2 + stroke_width + touchable_width // vertical radius with padding

        // Check if the point (x, y) is within the outer bounds of the oval
        val withinOuterBounds = ((x - cx) * (x - cx)) / (rx * rx) + ((y - cy) * (y - cy)) / (ry * ry) <= 1

        // Check if the point (x, y) is within the inner bounds (excluding the padding)
        val cxInner = this.x + this.width / 2 // center X of the inner oval
        val cyInner = this.y + this.height / 2 // center Y of the inner oval
        val rxInner = this.width / 2 - touchable_width // inner horizontal radius
        val ryInner = this.height / 2 - touchable_width // inner vertical radius

        val withinInnerBounds = ((x - cxInner) * (x - cxInner)) / (rxInner * rxInner) + ((y - cyInner) * (y - cyInner)) / (ryInner * ryInner) <= 1

        // Return true if the point is within the outer bounds and outside the inner bounds
        return withinOuterBounds && !withinInnerBounds
    }


    override fun toggleActive(isActive: Boolean): OvalState {
        return copy(isActive = isActive)
    }

    override fun copy(newX: Float, newY: Float, newWidth: Float, newHeight: Float): ShapeState {
        return copy(
            x = newX,
            y = newY,
            width = newWidth,
            height = newHeight
        )
    }

    override fun onDrawShape(drawScope: DrawScope) {
        drawScope.drawOval(
            color = Color.Red,
            topLeft = Offset(x, y),
            size = Size(width, height),
            style = Stroke(width = stroke_width)
        )
    }

}