package com.neversad.vidzerunok.feature.editor.shapes

import com.neversad.vidzerunok.feature.editor.model.ShapeData

interface ShapeTapDetector {

    fun detectTap(shape: ShapeData, x: Float, y: Float): Boolean
}