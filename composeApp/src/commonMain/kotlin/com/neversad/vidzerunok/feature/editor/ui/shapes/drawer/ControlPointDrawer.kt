package com.neversad.vidzerunok.feature.editor.ui.shapes.drawer

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope

object ControlPointDrawer {

    private const val CONTROL_POINT_RADIUS = 10f
    private const val CONTROL_POINT_STROKE = 1f
    private val CONTROL_POINT_COLOR = Color.Black
    private val CONTROL_POINT_INNER_COLOR = Color.White


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