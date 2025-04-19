package com.neversad.vidzerunok.feature.editor.ui

import com.neversad.vidzerunok.feature.editor.ui.shapes.ShapeState


data class EditorState(
    val file: String,
    val shapes: List<ShapeState> = emptyList(),
)