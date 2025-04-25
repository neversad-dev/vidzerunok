package com.neversad.vidzerunok.feature.editor.model



data class ShapeData(
    val id: Long,
    val shapeType: ShapeType,
    val isActive: Boolean,
    val activeInteractionMode: InteractionMode,
    val startX: Float,
    val startY: Float,
    val endX: Float,
    val endY: Float,
)