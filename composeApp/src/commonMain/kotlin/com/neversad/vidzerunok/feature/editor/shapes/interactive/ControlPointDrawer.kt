package com.neversad.vidzerunok.feature.editor.shapes.interactive

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.neversad.vidzerunok.feature.editor.shapes.ShapeConstants.CONTROL_POINT_ACTIVE_SCALE
import com.neversad.vidzerunok.feature.editor.shapes.ShapeConstants.CONTROL_POINT_COLOR
import com.neversad.vidzerunok.feature.editor.shapes.ShapeConstants.CONTROL_POINT_INNER_COLOR
import com.neversad.vidzerunok.feature.editor.shapes.ShapeConstants.CONTROL_POINT_RADIUS
import com.neversad.vidzerunok.feature.editor.shapes.ShapeConstants.CONTROL_POINT_STROKE


fun DrawScope.drawControlPoint(x: Float, y: Float, isActive: Boolean =  false) {
    val scale = if (isActive) CONTROL_POINT_ACTIVE_SCALE else 1f
    drawCircle(
        color = CONTROL_POINT_COLOR,
        radius = scale * CONTROL_POINT_RADIUS + CONTROL_POINT_STROKE * 2,
        center = Offset(x, y)
    )
    drawCircle(
        color = CONTROL_POINT_INNER_COLOR,
        radius = scale * CONTROL_POINT_RADIUS,
        center = Offset(x, y)
    )
}