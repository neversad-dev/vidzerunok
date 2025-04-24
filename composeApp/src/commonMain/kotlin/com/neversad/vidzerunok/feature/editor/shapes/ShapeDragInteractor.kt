package com.neversad.vidzerunok.feature.editor.shapes

import com.neversad.vidzerunok.feature.editor.model.ShapeData

interface ShapeDragInteractor {

    fun detectDrag(shape: ShapeData, x: Float, y: Float): Boolean

    fun applyDrag(shape: ShapeData, x: Float, y: Float): ShapeData

    fun finishDrag() {}

}