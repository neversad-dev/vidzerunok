package com.neversad.vidzerunok.feature.editor.shapes

import com.neversad.vidzerunok.feature.editor.model.ShapeData
import com.neversad.vidzerunok.feature.editor.model.ShapeType
import com.neversad.vidzerunok.feature.editor.shapes.arrow.ArrowTapDetector
import com.neversad.vidzerunok.feature.editor.shapes.base.ShapeTapDetector
import com.neversad.vidzerunok.feature.editor.shapes.oval.OvalTapDetector
import com.neversad.vidzerunok.feature.editor.shapes.rectangle.RectangleTapDetector

object CompositeShapeTapDetector: ShapeTapDetector {

    private val allDetectors = mapOf(
        ShapeType.RECTANGLE to RectangleTapDetector,
        ShapeType.OVAL to OvalTapDetector,
        ShapeType.ARROW to ArrowTapDetector
    )

    override fun detectTap(shape: ShapeData, x: Float, y: Float): Boolean {
        allDetectors[shape.shapeType]?.let { detector ->
            return detector.detectTap(shape, x, y)
        } ?: throw IllegalArgumentException("Unknown shape type: ${shape.shapeType}")
    }

}