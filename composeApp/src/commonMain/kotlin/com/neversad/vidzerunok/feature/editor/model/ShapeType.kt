package com.neversad.vidzerunok.feature.editor.model
import com.neversad.vidzerunok.feature.editor.model.ControlPoint.BOTTOM_LEFT
import com.neversad.vidzerunok.feature.editor.model.ControlPoint.BOTTOM_RIGHT
import com.neversad.vidzerunok.feature.editor.model.ControlPoint.TOP_LEFT
import com.neversad.vidzerunok.feature.editor.model.ControlPoint.TOP_RIGHT

enum class ControlPoint {
    TOP_LEFT,
    TOP_RIGHT,
    BOTTOM_LEFT,
    BOTTOM_RIGHT,
}

enum class ShapeType(vararg val controlPoints: ControlPoint) {
    RECTANGLE(
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT
    ),
    OVAL(
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT
    ),
    ARROW(
        TOP_LEFT,
        BOTTOM_RIGHT,
    )
}