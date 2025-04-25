package com.neversad.vidzerunok.feature.editor.shapes

import com.neversad.vidzerunok.feature.editor.model.ShapeData

interface ShapeInteractor {

    fun detectInteraction(shape: ShapeData, x: Float, y: Float): ShapeData

    fun applyInteraction(shape: ShapeData, x: Float, y: Float): ShapeData

    fun finishInteraction(shape: ShapeData): ShapeData = shape

}