package com.neversad.vidzerunok.feature.editor.domain

import kotlinx.datetime.Clock

class GenerateUidUseCase {
    operator fun invoke(): Long {
        return Clock.System.now().toEpochMilliseconds()
    }
}