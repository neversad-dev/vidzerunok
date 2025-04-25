package com.neversad.vidzerunok.feature.editor.shapes

import com.neversad.vidzerunok.feature.editor.model.InteractionMode.MOVE
import com.neversad.vidzerunok.feature.editor.model.InteractionMode.NONE
import com.neversad.vidzerunok.feature.editor.model.InteractionMode.RESIZE_BL
import com.neversad.vidzerunok.feature.editor.model.InteractionMode.RESIZE_BOTTOM
import com.neversad.vidzerunok.feature.editor.model.InteractionMode.RESIZE_BR
import com.neversad.vidzerunok.feature.editor.model.InteractionMode.RESIZE_LEFT
import com.neversad.vidzerunok.feature.editor.model.InteractionMode.RESIZE_RIGHT
import com.neversad.vidzerunok.feature.editor.model.InteractionMode.RESIZE_TL
import com.neversad.vidzerunok.feature.editor.model.InteractionMode.RESIZE_TOP
import com.neversad.vidzerunok.feature.editor.model.InteractionMode.RESIZE_TR
import com.neversad.vidzerunok.feature.editor.model.ShapeData
import com.neversad.vidzerunok.feature.editor.shapes.interactive.movable.MovableShapeInteractor
import com.neversad.vidzerunok.feature.editor.shapes.interactive.resizable.BottomLeftResizableInteractor
import com.neversad.vidzerunok.feature.editor.shapes.interactive.resizable.BottomResizableInteractor
import com.neversad.vidzerunok.feature.editor.shapes.interactive.resizable.BottomRightResizableInteractor
import com.neversad.vidzerunok.feature.editor.shapes.interactive.resizable.LeftResizableInteractor
import com.neversad.vidzerunok.feature.editor.shapes.interactive.resizable.RightResizableInteractor
import com.neversad.vidzerunok.feature.editor.shapes.interactive.resizable.TopLeftResizableInteractor
import com.neversad.vidzerunok.feature.editor.shapes.interactive.resizable.TopResizableInteractor
import com.neversad.vidzerunok.feature.editor.shapes.interactive.resizable.TopRightResizableInteractor

object CompositeShapeInteractor : ShapeInteractor {

    private val interactors = mapOf(
        RESIZE_TL to TopLeftResizableInteractor,
        RESIZE_TOP to TopResizableInteractor,
        RESIZE_TR to TopRightResizableInteractor,
        RESIZE_RIGHT to RightResizableInteractor,
        RESIZE_BL to BottomLeftResizableInteractor,
        RESIZE_BOTTOM to BottomResizableInteractor,
        RESIZE_BR to BottomRightResizableInteractor,
        RESIZE_LEFT to LeftResizableInteractor,
        MOVE to MovableShapeInteractor,
    )

    override fun detectInteraction(shape: ShapeData, x: Float, y: Float): ShapeData {
        shape.shapeType.interactionModes.forEach { interactionMode ->
            interactors[interactionMode]?.let { interactor ->
                val resultShape = interactor.detectInteraction(shape, x, y)
                if(resultShape.activeInteractionMode != NONE) {
                    return resultShape
                }
            } ?: throw IllegalArgumentException("Unknown shape interaction mode: $interactionMode")
        }
        return shape
    }

    override fun applyInteraction(shape: ShapeData, x: Float, y: Float): ShapeData {
        return interactors[shape.activeInteractionMode]?.applyInteraction(shape, x, y) ?: shape
    }

    override fun finishInteraction(shape: ShapeData): ShapeData {
        return shape.copy(activeInteractionMode = NONE)
    }
}