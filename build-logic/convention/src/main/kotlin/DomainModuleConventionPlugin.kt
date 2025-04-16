import com.neversad.vidzerunok.buildlogic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project

class DomainModuleConventionPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        plugins.apply(libs.findPlugin("vidzerunok.kotlinMultiplatform").get().get().pluginId)
    }
}