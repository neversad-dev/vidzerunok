package com.neversad.vidzerunok.feature.editor.shapes.common.resizable

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.neversad.vidzerunok.feature.editor.model.ControlPoint
import com.neversad.vidzerunok.feature.editor.model.ShapeData
import com.neversad.vidzerunok.feature.editor.shapes.ShapeDrawer
import com.neversad.vidzerunok.feature.editor.shapes.common.ShapeConstants.CONTROL_POINT_COLOR
import com.neversad.vidzerunok.feature.editor.shapes.common.ShapeConstants.CONTROL_POINT_INNER_COLOR
import com.neversad.vidzerunok.feature.editor.shapes.common.ShapeConstants.CONTROL_POINT_RADIUS
import com.neversad.vidzerunok.feature.editor.shapes.common.ShapeConstants.CONTROL_POINT_STROKE

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
        shapeState.shapeType.controlPoints.forEach {
            when(it) {
                ControlPoint.TOP_LEFT -> {
                    drawControlPoint(drawScope, shapeState.startX, shapeState.startY)
                }
                ControlPoint.TOP -> {
                    drawControlPoint(drawScope, shapeState.startX + (shapeState.endX - shapeState.startX) / 2, shapeState.startY)
                }
                ControlPoint.TOP_RIGHT -> {
                    drawControlPoint(drawScope, shapeState.endX, shapeState.startY)
                }
                ControlPoint.RIGHT -> {
                    drawControlPoint(drawScope, shapeState.endX, shapeState.startY + (shapeState.endY - shapeState.startY) / 2)
                }
                ControlPoint.BOTTOM_LEFT -> {
                    drawControlPoint(drawScope, shapeState.startX, shapeState.endY)
                }
                ControlPoint.BOTTOM -> {
                    drawControlPoint(drawScope, shapeState.startX + (shapeState.endX - shapeState.startX) / 2, shapeState.endY)
                }
                ControlPoint.BOTTOM_RIGHT -> {
                    drawControlPoint(drawScope, shapeState.endX, shapeState.endY)
                }
                ControlPoint.LEFT -> {
                    drawControlPoint(drawScope, shapeState.startX, shapeState.startY + (shapeState.endY - shapeState.startY) / 2)
                }
            }
        }
    }

    private fun drawControlPoint(drawScope: DrawScope, x: Float, y: Float) {
        drawScope.drawCircle(
            color = CONTROL_POINT_COLOR,
            radius = CONTROL_POINT_RADIUS + CONTROL_POINT_STROKE * 2,
            center = Offset(x, y)
        )
        drawScope.drawCircle(
            color = CONTROL_POINT_INNER_COLOR,
            radius = CONTROL_POINT_RADIUS,
            center = Offset(x, y)
        )
    }
}