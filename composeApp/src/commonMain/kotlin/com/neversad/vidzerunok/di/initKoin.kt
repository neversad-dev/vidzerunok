package com.neversad.vidzerunok.di

import com.neversad.vidzerunok.feature.editor.di.editorFeatureModule
import com.neversad.vidzerunok.feature.gallery.di.galleryFeatureModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)

        modules(sharedModule)

        modules(galleryFeatureModule)
        modules(editorFeatureModule)
    }

}