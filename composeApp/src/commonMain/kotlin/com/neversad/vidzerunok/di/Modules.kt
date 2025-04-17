package com.neversad.vidzerunok.di

import com.neversad.vidzerunok.core.data.InternalMemoryImageDataSource
import com.neversad.vidzerunok.feature.editor.ui.EditorViewModel
import com.neversad.vidzerunok.feature.gallery.data.ImageDataSource
import com.neversad.vidzerunok.feature.gallery.data.MainImageRepository
import com.neversad.vidzerunok.feature.gallery.domain.GenerateFileNameUseCase
import com.neversad.vidzerunok.feature.gallery.domain.GetTimestampUseCase
import com.neversad.vidzerunok.feature.gallery.domain.ImageRepository
import com.neversad.vidzerunok.feature.gallery.ui.collection.CollectionViewModel
import com.neversad.vidzerunok.feature.gallery.ui.file_picker.FilePickerViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val sharedModule = module {
    single { GetTimestampUseCase }
    single { GenerateFileNameUseCase(get()) }

    singleOf(::MainImageRepository).bind<ImageRepository>()

    singleOf(::InternalMemoryImageDataSource).bind<ImageDataSource>()

    viewModelOf(::FilePickerViewModel)
    viewModelOf(::CollectionViewModel)
    viewModelOf(::EditorViewModel)
}