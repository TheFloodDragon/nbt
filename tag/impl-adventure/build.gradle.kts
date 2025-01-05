kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(projects.tagApi)
                compileOnly(libs.adventure.api)
                compileOnly(libs.adventure.nbt)
            }
        }
    }
}