package com.neversad.vidzerunok.feature.editor.shapes.base

import androidx.compose.ui.graphics.drawscope.DrawScope
import com.neversad.vidzerunok.feature.editor.model.ShapeData

class ResizableShapeDrawer(
    private val childShapeDrawer: ShapeDrawer
) : ShapeDrawer {

    override fun drawShape(drawScope: DrawScope, shapeState: ShapeData) {
        childShapeDrawer.drawShape(drawScope, shapeState)

        if (shapeState.isActive) {
            drawControlPoints(drawScope, shapeState)
        }
    }

    private fun drawControlPoints(
        drawScope: DrawScope,
        shapeState: ShapeData
    )  {
        ControlPointDrawer.drawShape(
            drawScope,
            shapeState.startX,
            shapeState.startY
        )

        ControlPointDrawer.drawShape(
            drawScope,
            shapeState.endX,
            shapeState.startY
        )

        ControlPointDrawer.drawShape(
            drawScope,
            shapeState.endX,
            shapeState.endY
        )

        ControlPointDrawer.drawShape(
            drawScope,
            shapeState.startX,
            shapeState.endY
        )
    }
}