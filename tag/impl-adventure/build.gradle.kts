kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":tag-api"))
                compileOnly(libs.adventure.api)
                compileOnly(libs.adventure.nbt)
            }
        }
    }
}