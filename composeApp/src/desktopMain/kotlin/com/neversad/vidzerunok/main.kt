package com.neversad.vidzerunok

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.github.vinceglb.filekit.FileKit

fun main() = application {

    // Initialize FileKit
    FileKit.init(appId = "com.neversad.vidzerunok")

    Window(
        onCloseRequest = ::exitApplication,
        title = "vidzerunok",
    ) {
        App()
    }
}