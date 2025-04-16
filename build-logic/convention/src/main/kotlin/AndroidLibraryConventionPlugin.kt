import com.android.build.gradle.LibraryExtension
import com.neversad.vidzerunok.buildlogic.convention.configureKotlinAndroid
import com.neversad.vidzerunok.buildlogic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project): Unit = with(target) {
        plugins.apply(libs.findPlugin("androidLibrary").get().get().pluginId)

        extensions.configure<LibraryExtension> {

            configureKotlinAndroid(this)
            defaultConfig.targetSdk = libs.findVersion("android-targetSdk").get().requiredVersion.toInt()


        }
    }
}