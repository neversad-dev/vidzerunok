package com.neversad.vidzerunok.feature.editor.shapes.interactive.resizable

import androidx.compose.ui.graphics.drawscope.DrawScope
import com.neversad.vidzerunok.feature.editor.model.InteractionMode
import com.neversad.vidzerunok.feature.editor.model.ShapeData
import com.neversad.vidzerunok.feature.editor.shapes.ShapeDrawer
import com.neversad.vidzerunok.feature.editor.shapes.interactive.drawControlPoint

object BottomRightResizableDrawer : ShapeDrawer {

    override fun drawShape(drawScope: DrawScope, shapeState: ShapeData) = with(shapeState) {
        drawScope.drawControlPoint(
            endX,
            endY,
            shapeState.activeInteractionMode == InteractionMode.RESIZE_BR
        )
    }
}