package com.neversad.vidzerunok.feature.editor.ui



data class EditorState(
    val file: String,
    val shapes: List<Shape> = emptyList(),
    val activeShape: Shape? = null
)