plugins {
    kotlin("plugin.serialization")
}

kotlin.sourceSets.configureEach {
    dependencies {
        compileOnly(projects.tag)
        compileOnly(libs.serialization.core)
    }
}