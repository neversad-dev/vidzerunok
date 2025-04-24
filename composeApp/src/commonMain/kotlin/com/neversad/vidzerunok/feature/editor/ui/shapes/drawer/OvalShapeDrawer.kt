package com.neversad.vidzerunok.feature.editor.ui.shapes.drawer

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import com.neversad.vidzerunok.feature.editor.model.ShapeData
import com.neversad.vidzerunok.feature.editor.model.ShapeType
import com.neversad.vidzerunok.feature.editor.ui.shapes.drawer.ShapeDrawer.Companion.STROKE_WIDTH

object OvalShapeDrawer : ShapeDrawer {

    override fun drawShape(drawScope: DrawScope, shapeState: ShapeData) {
        if (shapeState.shapeType != ShapeType.OVAL) throw Exception("Invalid shape type for OvalShapeDrawer")

        val topLeft = Offset(shapeState.startX, shapeState.startY)
        val size = Size(shapeState.endX - shapeState.startX, shapeState.endY - shapeState.startY)
        drawScope.drawOval(
            color = Color.Red,
            topLeft = topLeft,
            size = size,
            style = Stroke(width = STROKE_WIDTH),
        )
    }
}