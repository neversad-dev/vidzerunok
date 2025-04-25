package com.neversad.vidzerunok.feature.editor.model

import com.neversad.vidzerunok.feature.editor.model.InteractionMode.MOVE
import com.neversad.vidzerunok.feature.editor.model.InteractionMode.RESIZE_BL
import com.neversad.vidzerunok.feature.editor.model.InteractionMode.RESIZE_BOTTOM
import com.neversad.vidzerunok.feature.editor.model.InteractionMode.RESIZE_BR
import com.neversad.vidzerunok.feature.editor.model.InteractionMode.RESIZE_LEFT
import com.neversad.vidzerunok.feature.editor.model.InteractionMode.RESIZE_RIGHT
import com.neversad.vidzerunok.feature.editor.model.InteractionMode.RESIZE_TL
import com.neversad.vidzerunok.feature.editor.model.InteractionMode.RESIZE_TOP
import com.neversad.vidzerunok.feature.editor.model.InteractionMode.RESIZE_TR


enum class ShapeType(vararg val interactionModes: InteractionMode) {
    RECTANGLE(
        RESIZE_TL,
        RESIZE_TOP,
        RESIZE_TR,
        RESIZE_RIGHT,
        RESIZE_BR,
        RESIZE_BOTTOM,
        RESIZE_BL,
        RESIZE_LEFT,
        MOVE,
    ),
    OVAL(
        RESIZE_TL,
        RESIZE_TR,
        RESIZE_BL,
        RESIZE_BR,
        MOVE,
    ),
    ARROW(
        RESIZE_TL,
        RESIZE_BR,
        MOVE,
    )
}