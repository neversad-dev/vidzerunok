package com.neversad.vidzerunok.feature.editor.shapes.base

import androidx.compose.ui.graphics.drawscope.DrawScope
import com.neversad.vidzerunok.feature.editor.model.ShapeData

interface ShapeDrawer {

    fun drawShape(drawScope: DrawScope, shapeState: ShapeData)
}
