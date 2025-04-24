package com.neversad.vidzerunok.feature.gallery.di

import com.neversad.vidzerunok.feature.gallery.data.MainImageRepository
import com.neversad.vidzerunok.feature.gallery.domain.GenerateFileNameUseCase
import com.neversad.vidzerunok.feature.gallery.domain.ImageRepository
import com.neversad.vidzerunok.feature.gallery.ui.collection.CollectionViewModel
import com.neversad.vidzerunok.feature.gallery.ui.file_picker.FilePickerViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val galleryFeatureModule = module {

    single { GenerateFileNameUseCase(get()) }

    singleOf(::MainImageRepository).bind<ImageRepository>()

    viewModelOf(::FilePickerViewModel)
    viewModelOf(::CollectionViewModel)

}