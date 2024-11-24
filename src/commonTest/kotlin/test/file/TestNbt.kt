package net.benwoodworth.knbt.test.file

import kotlinx.serialization.Serializable
import net.benwoodworth.knbt.NbtName
import net.benwoodworth.knbt.tag.NbtCompound
import net.benwoodworth.knbt.tag.buildNbtCompound
import net.benwoodworth.knbt.tag.put
import net.benwoodworth.knbt.test.assertStructureEquals

val testTag: NbtCompound
    get() = buildNbtCompound("hello world") {
        put("name", "Bananrama")
    }

@Serializable
@NbtName("hello world")
data class TestNbt(
    val name: String,
)

fun assertStructureEquals(expected: TestNbt, actual: TestNbt, message: String? = null): Unit =
    assertStructureEquals(expected, actual, message) {
        property("name") { name }
    }

val testClass: TestNbt
    get() = TestNbt(
        name = "Bananrama",
    )
