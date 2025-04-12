package com.neversad.vidzerunok.di

import com.neversad.vidzerunok.editor.data.InternalMemoryImageRepository
import com.neversad.vidzerunok.editor.domain.GenerateFileNameUseCase
import com.neversad.vidzerunok.editor.domain.GetTimestampUseCase
import com.neversad.vidzerunok.editor.domain.ImageRepository
import com.neversad.vidzerunok.editor.presentation.editor.EditorViewModel
import com.neversad.vidzerunok.editor.presentation.file_picker.FilePickerViewModel
import com.neversad.vidzerunok.editor.presentation.gallery.GalleryViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val sharedModule = module {
    single { GetTimestampUseCase }
    single { GenerateFileNameUseCase(get()) }

    singleOf(::InternalMemoryImageRepository).bind<ImageRepository>()

    viewModelOf(::FilePickerViewModel)
    viewModelOf(::GalleryViewModel)
    viewModelOf(::EditorViewModel)
}