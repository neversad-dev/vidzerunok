
import com.neversad.vidzerunok.buildlogic.convention.configureKotlinMultiplatform
import com.neversad.vidzerunok.buildlogic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KotlinMultiplatformConventionPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        plugins.apply(libs.findPlugin("kotlinMultiplatform").get().get().pluginId)

        extensions.configure<KotlinMultiplatformExtension> {
            configureKotlinMultiplatform(this)
        }
    }
}