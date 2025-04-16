package com.neversad.vidzerunok.di

import com.neversad.vidzerunok.core.data.InternalMemoryImageRepository
import com.neversad.vidzerunok.core.domain.GenerateFileNameUseCase
import com.neversad.vidzerunok.core.domain.GetTimestampUseCase
import com.neversad.vidzerunok.core.domain.ImageRepository
import com.neversad.vidzerunok.feature.editor.EditorViewModel
import com.neversad.vidzerunok.feature.gallery.collection.CollectionViewModel
import com.neversad.vidzerunok.feature.gallery.file_picker.FilePickerViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val sharedModule = module {
    single { GetTimestampUseCase }
    single { GenerateFileNameUseCase(get()) }

    singleOf(::InternalMemoryImageRepository).bind<ImageRepository>()

    viewModelOf(::FilePickerViewModel)
    viewModelOf(::CollectionViewModel)
    viewModelOf(::EditorViewModel)
}