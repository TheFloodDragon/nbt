kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(projects.tagCore)
                compileOnly(libs.adventure.api)
                compileOnly(libs.adventure.nbt)
            }
        }
    }
}