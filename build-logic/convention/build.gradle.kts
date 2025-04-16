plugins {
    `kotlin-dsl`
}

group = "com.neversad.vidzerunok.buildlogic"

dependencies{
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.android.gradle.plugin)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("kotlinMultiplatform") {
            id = libs.plugins.vidzerunok.kotlinMultiplatform.get().pluginId
            implementationClass = "KotlinMultiplatformConventionPlugin"
        }

        register("androidLibrary") {
            id = libs.plugins.vidzerunok.androidLibrary.get().pluginId
            implementationClass = "AndroidLibraryConventionPlugin"
        }

        register("androidApplication") {
            id = libs.plugins.vidzerunok.androidApplication.get().pluginId
            implementationClass = "AndroidApplicationConventionPlugin"
        }

        register("domainModule") {
            id = libs.plugins.vidzerunok.domainModule.get().pluginId
            implementationClass = "DomainModuleConventionPlugin"
        }

        register("dataModule") {
            id = libs.plugins.vidzerunok.dataModule.get().pluginId
            implementationClass = "DataModuleConventionPlugin"
        }

        register("featureModule") {
            id = libs.plugins.vidzerunok.featureModule.get().pluginId
            implementationClass = "FeatureModuleConventionPlugin"
        }
    }
}