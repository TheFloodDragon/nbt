rootProject.name = "nbt"

include("tag-api")
project(":tag-api").projectDir = file("tag/api")
include("tag-native")
project(":tag-native").projectDir = file("tag/impl-native")
include("tag-adventure")
project(":tag-adventure").projectDir = file("tag/impl-adventure")