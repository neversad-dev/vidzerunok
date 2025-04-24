package com.neversad.vidzerunok.feature.editor.di

import com.neversad.vidzerunok.feature.editor.data.InMemoryEditorShapesDataSource
import com.neversad.vidzerunok.feature.editor.domain.AddShapeUseCase
import com.neversad.vidzerunok.feature.editor.domain.ClearShapesUseCase
import com.neversad.vidzerunok.feature.editor.domain.DetectTapUseCase
import com.neversad.vidzerunok.feature.editor.domain.EditorShapesDataSource
import com.neversad.vidzerunok.feature.editor.domain.GenerateUidUseCase
import com.neversad.vidzerunok.feature.editor.domain.ObserveShapesUseCase
import com.neversad.vidzerunok.feature.editor.shapes.CompositeShapeDrawer
import com.neversad.vidzerunok.feature.editor.shapes.CompositeShapeTapDetector
import com.neversad.vidzerunok.feature.editor.shapes.base.ShapeDrawer
import com.neversad.vidzerunok.feature.editor.shapes.base.ShapeTapDetector
import com.neversad.vidzerunok.feature.editor.ui.EditorViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val editorFeatureModule = module {
    viewModelOf(::EditorViewModel)

    singleOf(::InMemoryEditorShapesDataSource).bind<EditorShapesDataSource>()

    singleOf(::GenerateUidUseCase)
    singleOf(::ObserveShapesUseCase)
    singleOf(::AddShapeUseCase)
    singleOf(::ClearShapesUseCase)
    singleOf(::DetectTapUseCase)

    single {CompositeShapeDrawer }.bind<ShapeDrawer>()
    single {CompositeShapeTapDetector }.bind<ShapeTapDetector>()
}

