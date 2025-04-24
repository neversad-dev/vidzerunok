package com.neversad.vidzerunok.feature.editor.ui.shapes.drawer

import androidx.compose.ui.graphics.drawscope.DrawScope
import com.neversad.vidzerunok.feature.editor.model.ShapeData

interface ShapeDrawer {
    companion object {
        const val STROKE_WIDTH = 10f
    }

    fun drawShape(drawScope: DrawScope, shapeState: ShapeData)
}
