package com.neversad.vidzerunok.core.data

import androidx.core.net.toUri
import io.github.vinceglb.filekit.PlatformFile

actual fun String.toPlatformFile() = PlatformFile(toUri())