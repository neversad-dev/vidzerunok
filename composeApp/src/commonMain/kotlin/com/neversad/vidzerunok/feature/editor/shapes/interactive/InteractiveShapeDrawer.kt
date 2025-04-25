package com.neversad.vidzerunok.feature.editor.shapes.interactive

import androidx.compose.ui.graphics.drawscope.DrawScope
import com.neversad.vidzerunok.feature.editor.model.InteractionMode.RESIZE_BL
import com.neversad.vidzerunok.feature.editor.model.InteractionMode.RESIZE_BOTTOM
import com.neversad.vidzerunok.feature.editor.model.InteractionMode.RESIZE_BR
import com.neversad.vidzerunok.feature.editor.model.InteractionMode.RESIZE_LEFT
import com.neversad.vidzerunok.feature.editor.model.InteractionMode.RESIZE_RIGHT
import com.neversad.vidzerunok.feature.editor.model.InteractionMode.RESIZE_TL
import com.neversad.vidzerunok.feature.editor.model.InteractionMode.RESIZE_TOP
import com.neversad.vidzerunok.feature.editor.model.InteractionMode.RESIZE_TR
import com.neversad.vidzerunok.feature.editor.model.ShapeData
import com.neversad.vidzerunok.feature.editor.shapes.ShapeDrawer
import com.neversad.vidzerunok.feature.editor.shapes.interactive.resizable.BottomLeftResizableDrawer
import com.neversad.vidzerunok.feature.editor.shapes.interactive.resizable.BottomResizableDrawer
import com.neversad.vidzerunok.feature.editor.shapes.interactive.resizable.BottomRightResizableDrawer
import com.neversad.vidzerunok.feature.editor.shapes.interactive.resizable.LeftResizableDrawer
import com.neversad.vidzerunok.feature.editor.shapes.interactive.resizable.RightResizableDrawer
import com.neversad.vidzerunok.feature.editor.shapes.interactive.resizable.TopLeftResizableDrawer
import com.neversad.vidzerunok.feature.editor.shapes.interactive.resizable.TopResizableDrawer
import com.neversad.vidzerunok.feature.editor.shapes.interactive.resizable.TopRightResizableDrawer

class InteractiveShapeDrawer(
    private val childShapeDrawer: ShapeDrawer
) : ShapeDrawer {

    private val interactibleDrawers = mapOf(
        RESIZE_TL to TopLeftResizableDrawer,
        RESIZE_TOP to TopResizableDrawer,
        RESIZE_TR to TopRightResizableDrawer,
        RESIZE_RIGHT to RightResizableDrawer,
        RESIZE_BL to BottomLeftResizableDrawer,
        RESIZE_BOTTOM to BottomResizableDrawer,
        RESIZE_BR to BottomRightResizableDrawer,
        RESIZE_LEFT to LeftResizableDrawer
    )

    override fun drawShape(drawScope: DrawScope, shapeState: ShapeData) {
        childShapeDrawer.drawShape(drawScope, shapeState)

        if (shapeState.isActive) {
            shapeState.shapeType.interactionModes.forEach {
                interactibleDrawers[it]?.drawShape(drawScope, shapeState)
            }
        }
    }
}