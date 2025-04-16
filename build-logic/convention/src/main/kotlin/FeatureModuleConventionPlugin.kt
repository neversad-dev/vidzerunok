
import com.neversad.vidzerunok.buildlogic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

class FeatureModuleConventionPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        plugins.apply(libs.findPlugin("vidzerunok.kotlinMultiplatform").get().get().pluginId)
        plugins.apply(libs.findPlugin("vidzerunok.androidLibrary").get().get().pluginId)
        plugins.apply(libs.findPlugin("composeMultiplatform").get().get().pluginId)
        plugins.apply(libs.findPlugin("composeCompiler").get().get().pluginId)
        plugins.apply(libs.findPlugin("jetbrains.kotlin.serialization").get().get().pluginId)

        extensions.configure<KotlinMultiplatformExtension> {

            androidTarget {
                @OptIn(ExperimentalKotlinGradlePluginApi::class)
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_17)
                }
            }

            sourceSets.configureEach {
                when (name) {
                    "commonMain" -> dependencies {
                        implementation(libs.findLibrary("kotlinx.serialization").get().get())
                        libs.findBundle("koin.compose").get().get().forEach {
                            implementation(it)
                        }
                    }
                }
            }
        }

    }
}