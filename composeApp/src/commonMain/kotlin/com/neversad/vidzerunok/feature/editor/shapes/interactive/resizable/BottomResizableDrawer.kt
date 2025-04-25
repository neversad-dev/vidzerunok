package com.neversad.vidzerunok.feature.editor.shapes.interactive.resizable

import androidx.compose.ui.graphics.drawscope.DrawScope
import com.neversad.vidzerunok.feature.editor.model.InteractionMode
import com.neversad.vidzerunok.feature.editor.model.ShapeData
import com.neversad.vidzerunok.feature.editor.shapes.ShapeDrawer
import com.neversad.vidzerunok.feature.editor.shapes.interactive.drawControlPoint

object BottomResizableDrawer : ShapeDrawer {

    override fun drawShape(drawScope: DrawScope, shapeState: ShapeData) = with(shapeState) {
        drawScope.drawControlPoint(
            (startX + endX) / 2,
            endY,
            shapeState.activeInteractionMode == InteractionMode.RESIZE_BOTTOM
        )
    }
}