package com.neversad.vidzerunok.feature.editor.shapes

import androidx.compose.ui.graphics.drawscope.DrawScope
import com.neversad.vidzerunok.feature.editor.model.ShapeData
import com.neversad.vidzerunok.feature.editor.model.ShapeType
import com.neversad.vidzerunok.feature.editor.shapes.arrow.ArrowShapeDrawer
import com.neversad.vidzerunok.feature.editor.shapes.interactive.InteractiveShapeDrawer
import com.neversad.vidzerunok.feature.editor.shapes.oval.OvalShapeDrawer
import com.neversad.vidzerunok.feature.editor.shapes.rectangle.RectangleShapeDrawer

object CompositeShapeDrawer : ShapeDrawer {

    private val allDrawers = mapOf(
        ShapeType.RECTANGLE to InteractiveShapeDrawer(RectangleShapeDrawer),
        ShapeType.OVAL to InteractiveShapeDrawer(OvalShapeDrawer),
        ShapeType.ARROW to InteractiveShapeDrawer(ArrowShapeDrawer)
    )


    override fun drawShape(drawScope: DrawScope, shapeState: ShapeData) {
        allDrawers[shapeState.shapeType]?.drawShape(drawScope, shapeState)
            ?: throw Exception("Invalid shape type for CompositeShapeDrawer")
    }
}