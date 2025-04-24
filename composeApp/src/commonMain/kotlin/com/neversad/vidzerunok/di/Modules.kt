package com.neversad.vidzerunok.di

import com.neversad.vidzerunok.core.data.InternalMemoryImageDataSource
import com.neversad.vidzerunok.core.domain.GetTimestampUseCase
import com.neversad.vidzerunok.feature.gallery.data.ImageDataSource
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val sharedModule = module {
    single { GetTimestampUseCase }


    singleOf(::InternalMemoryImageDataSource).bind<ImageDataSource>()

}