package com.neversad.vidzerunok.feature.editor.shapes

import com.neversad.vidzerunok.feature.editor.model.ControlPoint
import com.neversad.vidzerunok.feature.editor.model.ShapeData
import com.neversad.vidzerunok.feature.editor.shapes.common.MovableShapeInteractor
import com.neversad.vidzerunok.feature.editor.shapes.common.resizable.BottomLeftResizableInteractor
import com.neversad.vidzerunok.feature.editor.shapes.common.resizable.BottomResizableInteractor
import com.neversad.vidzerunok.feature.editor.shapes.common.resizable.BottomRightResizableInteractor
import com.neversad.vidzerunok.feature.editor.shapes.common.resizable.LeftResizableInteractor
import com.neversad.vidzerunok.feature.editor.shapes.common.resizable.RightResizableInteractor
import com.neversad.vidzerunok.feature.editor.shapes.common.resizable.TopLeftResizableInteractor
import com.neversad.vidzerunok.feature.editor.shapes.common.resizable.TopResizableInteractor
import com.neversad.vidzerunok.feature.editor.shapes.common.resizable.TopRightResizableInteractor

object CompositeShapeDragInteractor : ShapeDragInteractor {

    private val allDragInteractors = mapOf(
        ControlPoint.TOP_LEFT to TopLeftResizableInteractor,
        ControlPoint.TOP to TopResizableInteractor,
        ControlPoint.TOP_RIGHT to TopRightResizableInteractor,
        ControlPoint.RIGHT to RightResizableInteractor,
        ControlPoint.BOTTOM_LEFT to BottomLeftResizableInteractor,
        ControlPoint.BOTTOM to BottomResizableInteractor,
        ControlPoint.BOTTOM_RIGHT to BottomRightResizableInteractor,
        ControlPoint.LEFT to LeftResizableInteractor
    )

    private var interactor: ShapeDragInteractor? = null

    override fun detectDrag(shape: ShapeData, x: Float, y: Float): Boolean {
        shape.shapeType.controlPoints.forEach {
            allDragInteractors[it]?.let { interactor ->
                if (interactor.detectDrag(shape, x, y)) {
                    this.interactor = interactor
                    return true
                }
            } ?: throw IllegalArgumentException("Unknown shape control point: $it")
        }
        this.interactor = MovableShapeInteractor
        return true
    }

    override fun applyDrag(shape: ShapeData, x: Float, y: Float): ShapeData {
        return interactor?.applyDrag(shape, x, y) ?: shape
    }

    override fun finishDrag() {
        interactor = null
    }
}