package com.neversad.vidzerunok.buildlogic.convention

import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.configureKotlinMultiplatform(
    extension: KotlinMultiplatformExtension
) = extension.apply {

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = moduleName
            isStatic = true
        }
    }

    jvm("desktop")


    applyDefaultHierarchyTemplate()
    sourceSets.configureEach {
        when (name) {
            "commonMain" -> dependencies {
                implementation(libs.findLibrary("koin.core").get().get())
            }
        }
    }
}