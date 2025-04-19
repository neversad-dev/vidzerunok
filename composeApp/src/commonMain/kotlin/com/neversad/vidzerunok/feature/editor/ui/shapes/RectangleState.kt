package com.neversad.vidzerunok.feature.editor.ui.shapes

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import kotlinx.datetime.Clock


private const val stroke_width = 10f
private const val touchable_width = 20f


data class RectangleState(
    override val id: Long = Clock.System.now().epochSeconds,
    override val isActive: Boolean = true,
    override val x: Float = 50f,
    override val y: Float = 50f,
    override val width: Float = 200f,
    override val height: Float = 300f,
) : ResizableShapeState() {



    override fun withinActivationBounds(x: Float, y: Float): Boolean {
        return (x in (this.x - stroke_width - touchable_width)..(this.x + this.width + stroke_width + touchable_width) &&
                y in (this.y - stroke_width - touchable_width)..(this.y + this.height + stroke_width + touchable_width)) &&
                !(x in this.x + touchable_width..(this.x + this.width - touchable_width) &&
                        y in this.y + touchable_width..(this.y + this.height - touchable_width))
    }


    override fun toggleActive(isActive: Boolean): RectangleState {
        return copy(isActive = isActive)
    }

    override fun copy(newX: Float, newY: Float, newWidth: Float, newHeight: Float): ShapeState {
        return copy(
            x = newX,
            y =  newY,
            width = newWidth,
            height = newHeight
        )
    }

    override fun onDrawShape(drawScope: DrawScope) {
        drawScope.drawRect(
            color = Color.Red,
            topLeft = Offset(x, y),
            size = Size(width, height),
            style = Stroke(width = stroke_width),
        )
    }



}
