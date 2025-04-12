package com.neversad.vidzerunok

import androidx.compose.ui.window.ComposeUIViewController
import com.neversad.vidzerunok.app.App
import com.neversad.vidzerunok.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }