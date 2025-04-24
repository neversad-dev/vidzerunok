package com.neversad.vidzerunok.feature.editor.model

import com.neversad.vidzerunok.feature.editor.model.ControlPoint.BOTTOM
import com.neversad.vidzerunok.feature.editor.model.ControlPoint.BOTTOM_LEFT
import com.neversad.vidzerunok.feature.editor.model.ControlPoint.BOTTOM_RIGHT
import com.neversad.vidzerunok.feature.editor.model.ControlPoint.RIGHT
import com.neversad.vidzerunok.feature.editor.model.ControlPoint.TOP
import com.neversad.vidzerunok.feature.editor.model.ControlPoint.TOP_LEFT
import com.neversad.vidzerunok.feature.editor.model.ControlPoint.*

enum class ControlPoint {
    TOP_LEFT,
    TOP,
    TOP_RIGHT,
    RIGHT,
    BOTTOM_RIGHT,
    BOTTOM,
    BOTTOM_LEFT,
    LEFT,
}

enum class ShapeType(vararg val controlPoints: ControlPoint) {
    RECTANGLE(
        TOP_LEFT,
        TOP,
        TOP_RIGHT,
        RIGHT,
        BOTTOM_RIGHT,
        BOTTOM,
        BOTTOM_LEFT,
        LEFT
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