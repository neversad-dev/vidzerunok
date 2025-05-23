package com.neversad.vidzerunok

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.neversad.vidzerunok.app.App
import com.neversad.vidzerunok.di.initKoin
import io.github.vinceglb.filekit.FileKit

fun main() = application {

    initKoin()

    // Initialize FileKit
    FileKit.init(appId = "com.neversad.vidzerunok")

    Window(
        onCloseRequest = ::exitApplication,
        title = "vidzerunok",
    ) {
        App()
    }
}