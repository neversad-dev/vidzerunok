package com.neversad.vidzerunok.feature.editor.ui

import androidx.compose.ui.geometry.Offset



data class Shape(
    val id: Long = 0,
    val x: Float = 50f,
    val y: Float = 50f,
    val width: Float = 200f,
    val height: Float = 300f,
) {

    val topLeft: Offset get() = Offset(x, y)

    val topRight: Offset get() = Offset(x + width, y)

    val bottomLeft: Offset get() = Offset(x, y + height)

    val bottomRight: Offset get() = Offset(x + width, y + height)

    val top: Offset get() = Offset(x + width / 2, y)

    val bottom: Offset get() = Offset(x + width / 2, y + height)

    val left: Offset get() = Offset(x, y + height / 2)

    val right: Offset get() = Offset(x + width, y + height / 2)

    val controlPoints: List<Offset> = listOf(
        topLeft,
        top,
        topRight,
        left,
        bottomRight,
        bottom,
        bottomLeft,
        right
    )


}
