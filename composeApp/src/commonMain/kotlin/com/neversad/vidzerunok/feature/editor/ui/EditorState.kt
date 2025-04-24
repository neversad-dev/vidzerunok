package com.neversad.vidzerunok.feature.editor.ui

import com.neversad.vidzerunok.feature.editor.model.ShapeData


data class EditorState(
    val file: String,
    val shapes: List<ShapeData> = emptyList(),
)