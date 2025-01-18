rootProject.name = "nbt"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

mapOf(
    "tag-core" to "tag/core",
    "tag-native" to "tag/impl-native",
    "tag-adventure" to "tag/impl-adventure"
).forEach { (name, path) ->
    include(name)
    project(":$name").projectDir = file(path)
}