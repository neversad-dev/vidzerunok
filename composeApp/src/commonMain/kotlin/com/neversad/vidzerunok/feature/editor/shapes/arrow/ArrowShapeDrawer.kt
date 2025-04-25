package com.neversad.vidzerunok.feature.editor.shapes.arrow

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.neversad.vidzerunok.feature.editor.model.ShapeData
import com.neversad.vidzerunok.feature.editor.model.ShapeType
import com.neversad.vidzerunok.feature.editor.shapes.ShapeDrawer
import com.neversad.vidzerunok.feature.editor.shapes.ShapeConstants.ARROW_HEAD_SIZE
import com.neversad.vidzerunok.feature.editor.shapes.ShapeConstants.ARROW_LINE_WIDTH
import com.neversad.vidzerunok.feature.editor.shapes.ShapeConstants.STROKE_WIDTH
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

object ArrowShapeDrawer : ShapeDrawer {

    override fun drawShape(drawScope: DrawScope, shapeState: ShapeData) {
        if (shapeState.shapeType != ShapeType.ARROW) throw Exception("Invalid shape type for ArrowShapeDrawer")

        with(drawScope) {
            val start = Offset(shapeState.startX, shapeState.startY)  // Start point of the arrow
            val end = Offset(shapeState.endX, shapeState.endY)    // End point of the arrow

            // Calculate the angle of the arrow
            val angle = atan2((end.y - start.y).toDouble(), (end.x - start.x).toDouble())

            // Calculate the base points of the white triangle
            val whiteArrowheadLeft = Offset(
                end.x - ARROW_HEAD_SIZE * cos(angle + PI / 6).toFloat(),
                end.y - ARROW_HEAD_SIZE * sin(angle + PI / 6).toFloat()
            )

            val whiteArrowheadRight = Offset(
                end.x - ARROW_HEAD_SIZE * cos(angle - PI / 6).toFloat(),
                end.y - ARROW_HEAD_SIZE * sin(angle - PI / 6).toFloat()
            )

            // Calculate the center point of the triangle base (where the line should end)
            val lineEnd = Offset(
                (whiteArrowheadLeft.x + whiteArrowheadRight.x) / 2,
                (whiteArrowheadLeft.y + whiteArrowheadRight.y) / 2
            )

            // Calculate the black outline points by extending each white triangle point outward
            val blackArrowTip = Offset(
                end.x + ARROW_LINE_WIDTH * cos(angle).toFloat(),
                end.y + ARROW_LINE_WIDTH * sin(angle).toFloat()
            )


            val blackArrowheadLeft = Offset(
                whiteArrowheadLeft.x - ARROW_LINE_WIDTH * cos(angle + PI / 2).toFloat(),
                whiteArrowheadLeft.y - ARROW_LINE_WIDTH * sin(angle + PI / 2).toFloat()
            )

            val blackArrowheadRight = Offset(
                whiteArrowheadRight.x - ARROW_LINE_WIDTH * cos(angle - PI / 2).toFloat(),
                whiteArrowheadRight.y - ARROW_LINE_WIDTH * sin(angle - PI / 2).toFloat()
            )

            // Draw the black outline of the line
            drawLine(
                color = Color.Black,
                start = start,
                end = lineEnd,
                strokeWidth = STROKE_WIDTH + ARROW_LINE_WIDTH
            )

            // Draw the white line
            drawLine(
                color = Color.White,
                start = start,
                end = lineEnd,
                strokeWidth = STROKE_WIDTH
            )

            // Draw the black arrowhead outline
            val blackPath = Path().apply {
                moveTo(blackArrowTip.x, blackArrowTip.y)
                lineTo(blackArrowheadLeft.x, blackArrowheadLeft.y)
                lineTo(blackArrowheadRight.x, blackArrowheadRight.y)
                close()
            }

            drawPath(
                color = Color.Black,
                path = blackPath
            )

            // Draw the white arrowhead
            val whitePath = Path().apply {
                moveTo(end.x, end.y)
                lineTo(whiteArrowheadLeft.x, whiteArrowheadLeft.y)
                lineTo(whiteArrowheadRight.x, whiteArrowheadRight.y)
                close()
            }

            drawPath(
                color = Color.White,
                path = whitePath
            )
        }
    }
}