package com.neversad.vidzerunok.feature.editor.shapes.base

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.neversad.vidzerunok.feature.editor.shapes.base.ShapeConstants.CONTROL_POINT_COLOR
import com.neversad.vidzerunok.feature.editor.shapes.base.ShapeConstants.CONTROL_POINT_INNER_COLOR
import com.neversad.vidzerunok.feature.editor.shapes.base.ShapeConstants.CONTROL_POINT_RADIUS
import com.neversad.vidzerunok.feature.editor.shapes.base.ShapeConstants.CONTROL_POINT_STROKE

object ControlPointDrawer {

    fun drawShape(drawScope: DrawScope, x: Float, y: Float) = with(drawScope) {
        drawCircle(
            color = CONTROL_POINT_COLOR,
            radius = CONTROL_POINT_RADIUS + CONTROL_POINT_STROKE * 2,
            center = Offset(x, y)
        )
        drawCircle(
            color = CONTROL_POINT_INNER_COLOR,
            radius = CONTROL_POINT_RADIUS,
            center = Offset(x, y)
        )
    }
}