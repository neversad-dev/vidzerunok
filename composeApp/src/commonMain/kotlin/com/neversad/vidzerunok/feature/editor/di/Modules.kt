package com.neversad.vidzerunok.feature.editor.di

import com.neversad.vidzerunok.feature.editor.data.InMemoryEditorShapesDataSource
import com.neversad.vidzerunok.feature.editor.domain.AddShapeUseCase
import com.neversad.vidzerunok.feature.editor.domain.ClearShapesUseCase
import com.neversad.vidzerunok.feature.editor.domain.EditorShapesDataSource
import com.neversad.vidzerunok.feature.editor.domain.GenerateUidUseCase
import com.neversad.vidzerunok.feature.editor.domain.GetShapesUseCase
import com.neversad.vidzerunok.feature.editor.ui.EditorViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val editorFeatureModule = module{
    viewModelOf(::EditorViewModel)

    singleOf(::InMemoryEditorShapesDataSource).bind<EditorShapesDataSource>()

    singleOf(::GenerateUidUseCase)
    singleOf(::GetShapesUseCase)
    singleOf(::AddShapeUseCase)
    singleOf (::ClearShapesUseCase)
}

