package com.neversad.vidzerunok.feature.editor.shapes

import androidx.compose.ui.graphics.drawscope.DrawScope
import com.neversad.vidzerunok.feature.editor.model.ShapeData
import com.neversad.vidzerunok.feature.editor.model.ShapeType
import com.neversad.vidzerunok.feature.editor.shapes.arrow.ArrowShapeDrawer
import com.neversad.vidzerunok.feature.editor.shapes.base.ResizableShapeDrawer
import com.neversad.vidzerunok.feature.editor.shapes.base.ShapeDrawer
import com.neversad.vidzerunok.feature.editor.shapes.oval.OvalShapeDrawer
import com.neversad.vidzerunok.feature.editor.shapes.rectangle.RectangleShapeDrawer

object CompositeShapeDrawer : ShapeDrawer{

    private val allDrawers = mapOf(
        ShapeType.RECTANGLE to ResizableShapeDrawer(RectangleShapeDrawer),
        ShapeType.OVAL to ResizableShapeDrawer(OvalShapeDrawer),
        ShapeType.ARROW to ArrowShapeDrawer
    )


    override fun drawShape(drawScope: DrawScope, shapeState: ShapeData) {
        allDrawers[shapeState.shapeType]?.drawShape(drawScope, shapeState)
            ?: throw Exception("Invalid shape type for CompositeShapeDrawer")
    }
}