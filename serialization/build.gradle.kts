plugins {
    kotlin("plugin.serialization")
}

kotlin.sourceSets {
    configureEach {
        dependencies {
            compileOnly(projects.tag)
            compileOnly(libs.serialization.core)
        }
    }
    val commonTest by getting {
        dependencies {
            implementation(kotlin("test"))
            implementation(projects.tag)
            implementation(libs.serialization.core)
            implementation(libs.serialization.json)
        }
    }
}