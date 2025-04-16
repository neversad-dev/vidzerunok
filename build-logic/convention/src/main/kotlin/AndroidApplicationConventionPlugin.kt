
import com.android.build.api.dsl.ApplicationExtension
import com.neversad.vidzerunok.buildlogic.convention.configureKotlinAndroid
import com.neversad.vidzerunok.buildlogic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {

    override fun apply(target: Project): Unit = with(target) {
        plugins.apply(libs.findPlugin("androidApplication").get().get().pluginId)

        extensions.configure<ApplicationExtension> {

            configureKotlinAndroid(this)
            defaultConfig.targetSdk = libs.findVersion("android-targetSdk").get().requiredVersion.toInt()


        }
    }
}