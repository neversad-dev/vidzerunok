package com.neversad.vidzerunok.feature.editor.shapes.oval

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import com.neversad.vidzerunok.feature.editor.model.ShapeData
import com.neversad.vidzerunok.feature.editor.model.ShapeType
import com.neversad.vidzerunok.feature.editor.shapes.common.ShapeConstants.STROKE_COLOR
import com.neversad.vidzerunok.feature.editor.shapes.common.ShapeConstants.STROKE_WIDTH
import com.neversad.vidzerunok.feature.editor.shapes.ShapeDrawer

object OvalShapeDrawer : ShapeDrawer {

    override fun drawShape(drawScope: DrawScope, shapeState: ShapeData) {
        if (shapeState.shapeType != ShapeType.OVAL) throw Exception("Invalid shape type for OvalShapeDrawer")

        val topLeft = Offset(shapeState.startX, shapeState.startY)
        val size = Size(shapeState.endX - shapeState.startX, shapeState.endY - shapeState.startY)
        drawScope.drawOval(
            color = STROKE_COLOR,
            topLeft = topLeft,
            size = size,
            style = Stroke(width = STROKE_WIDTH),
        )
    }
}