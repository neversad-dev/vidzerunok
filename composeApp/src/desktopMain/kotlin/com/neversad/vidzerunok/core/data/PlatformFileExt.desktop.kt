package com.neversad.vidzerunok.core.data

import io.github.vinceglb.filekit.PlatformFile

actual fun String.toPlatformFile() = PlatformFile(this)