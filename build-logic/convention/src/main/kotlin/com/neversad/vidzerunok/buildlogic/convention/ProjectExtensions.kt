package com.neversad.vidzerunok.buildlogic.convention

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.internal.extensions.stdlib.capitalized

internal val Project.libs get(): VersionCatalog =
    extensions.findByType(VersionCatalogsExtension::class.java)!!.named("libs")

internal val Project.moduleName
    get() = path
        .split(":")
        .filter { it.isNotBlank() }
        .joinToString("") { it.capitalized() }

internal val Project.modulePackageName
    get() = path
        .split(":")
        .filter { it.isNotBlank() }
        .joinToString(".") { it.lowercase() }