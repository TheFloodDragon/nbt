import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

plugins {
    kotlin("multiplatform") version libs.versions.kotlin apply false
    kotlin("plugin.serialization") version libs.versions.kotlin apply false
    id("org.jetbrains.kotlinx.binary-compatibility-validator") version "0.16.3"
    id("org.jetbrains.dokka") version "1.9.20"
    `maven-publish`
}

subprojects {

    apply(plugin = "org.jetbrains.kotlin.multiplatform")
    apply(plugin = "maven-publish")

    repositories {
        mavenCentral()
    }

    configure<KotlinMultiplatformExtension> {
        explicitApi()

        jvm()

        jvm {
            compilerOptions {
                jvmTarget = JvmTarget.JVM_1_8
            }
        }
    }

    tasks.withType<Test> {
        ignoreFailures = true
    }

    publishing {
        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/TheFloodDragon/nbt")
                credentials {
                    username = System.getenv("GITHUB_ACTOR")
                    password = System.getenv("GITHUB_TOKEN")
                }
            }
        }
        publications.withType<MavenPublication> {

            pom {
                name.set("nbt")
                description.set("Minecraft NBT API written by Kotlin.")
                url.set("https://github.com/Altawk/nbt")

                licenses {
                    license {
                        name.set("GNU Lesser General Public License")
                        url.set("https://www.gnu.org/licenses/lgpl-3.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("TheFloodDragon")
                        name.set("Flood Dragon")
                        email.set("theflooddragon@foxmail.com")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com:Altawk/nbt.git")
                    developerConnection.set("scm:git:ssh://github.com:Altawk/nbt.git")
                    url.set("https://github.com/Altawk/nbt")
                }
            }
        }
    }

}

//apiValidation {
//    nonPublicMarkers.add("net.benwoodworth.knbt.NbtDeprecated")
//}

//kotlin {
//    explicitApi()
//
//    jvm {
//        @OptIn(ExperimentalKotlinGradlePluginApi::class)
//        compilerOptions {
//            jvmTarget = JvmTarget.JVM_1_8
//        }
//        testRuns["test"].executionTask.configure {
//            useJUnitPlatform()
//        }
//    }
//
//    js(IR) {
//        browser {
//            testTask {
//                useKarma {
//                    useFirefoxHeadless()
//                    useChromeHeadless()
//                }
//            }
//        }
//        nodejs()
//    }
//
//    //wasmJs() // Requires gzip/zlib support to be implemented
//    //wasmWasi()
//
//    //wasmJs()   // Requires gzip/zlib support to be implemented
//    //wasmWasi() //
//
//    linuxX64()
//    linuxArm64()
//    //androidNativeArm32() // Not supported by Okio yet
//    //androidNativeArm64() // https://github.com/square/okio/issues/1242#issuecomment-1759357336
//    //androidNativeX86()
//    //androidNativeX64()
//    linuxArm64()
//    //androidNativeArm32() // Not supported by Okio yet
//    //androidNativeArm64() // https://github.com/square/okio/issues/1242#issuecomment-1759357336
//    //androidNativeX86()   //
//    //androidNativeX64()   //
//    macosX64()
//    macosArm64()
//    iosSimulatorArm64()
//    macosArm64()
//    iosSimulatorArm64()
//    iosX64()
//    watchosSimulatorArm64()
//    watchosX64()
//    watchosSimulatorArm64()
//    watchosX64()
//    watchosArm32()
//    watchosArm64()
//    tvosSimulatorArm64()
//    tvosX64()
//    tvosArm64()
//    iosArm64()
//    watchosDeviceArm64()
//    tvosSimulatorArm64()
//    tvosX64()
//    tvosArm64()
//    iosArm64()
//    watchosDeviceArm64()
//    mingwX64()
//
//    sourceSets {
//        configureEach {
//            languageSettings.apply {
//                optIn("kotlin.contracts.ExperimentalContracts")
//                optIn("net.benwoodworth.knbt.InternalNbtApi")
//                optIn("net.benwoodworth.knbt.MIGRATION Acknowledge that NbtCompound now has a stricter get")
//            }
//        }
//
//        val commonMain by getting {
//            dependencies {
//                api("org.jetbrains.kotlinx:kotlinx-serialization-core:$kotlinx_serialization_version")
//                implementation("com.squareup.okio:okio:$okio_version")
//                implementation("com.squareup.okio:okio:$okio_version")
//            }
//        }
//        //       val commonTest by getting {
//            dependencies {
//                implementation(kotlin("test"))
//                implementation("com.benwoodworth.parameterize:parameterize:$parameterize_version")
//            }
//        }
//        val jvmTest by getting {
//            dependencies {
//                implementation(kotlin("reflect"))
//                implementation(kotlin("test-junit5"))
//            }
//        }
//        val jsMain by getting {
//            dependencies {
//                implementation(npm("pako", "2.0.3"))
//            }
//        }
//        val jsTest by getting {
//            dependencies {
//                // https://github.com/square/okio/issues/1163
//                implementation(devNpm("node-polyfill-webpack-plugin", "^2.0.1"))
//            }
//        }
//    }
//}

//tasks.named<Test>("jvmTest") {
//    useJUnitPlatform()
//}


//
//tasks.withType<DokkaTask> {
//    dokkaSourceSets.all {
//        includeNonPublic.set(false)
//        skipDeprecated.set(true)
//    }
//}
//
//val javadocJar by tasks.registering(Jar::class) {
//    archiveClassifier.set("javadoc")
//}
//