import cn.altawk.nbt.tag.*
import kotlinx.serialization.json.*

// A lot of nbt's functionality is designed to match kotlinx.serialization's JSON format, so these help ensure that
// serialization behavior is correctly matched

fun NbtTag.toJsonElement(): JsonElement = when (this) {
    is NbtByte -> JsonPrimitive(content)
    is NbtShort -> JsonPrimitive(content)
    is NbtInt -> JsonPrimitive(content)
    is NbtLong -> JsonPrimitive(content)
    is NbtFloat -> JsonPrimitive(content)
    is NbtDouble -> JsonPrimitive(content)

    is NbtString -> JsonPrimitive(content)

    is NbtList -> JsonArray(map { it.toJsonElement() })

    is NbtByteArray -> JsonArray(content.map { JsonPrimitive(it) })
    is NbtIntArray -> JsonArray(content.map { JsonPrimitive(it) })
    is NbtLongArray -> JsonArray(content.map { JsonPrimitive(it) })

    is NbtCompound -> JsonObject(mapValues { (_, value) -> value.toJsonElement() })
}

fun JsonElement.toNbtTag(): NbtTag = when (this) {
    is JsonPrimitive -> when {
        booleanOrNull != null -> NbtByte(boolean)
        intOrNull != null -> NbtInt(int)
        longOrNull != null -> NbtLong(long)
        floatOrNull != null -> NbtFloat(float)
        doubleOrNull != null -> NbtDouble(double)
        isString -> NbtString(content)
        else -> throw IllegalArgumentException("Unsupported JsonPrimitive type")
    }

    is JsonArray -> {
        val firstElement = firstOrNull()
        when {
            firstElement is JsonPrimitive && firstElement.booleanOrNull != null -> NbtByteArray(map { it.jsonPrimitive.content.toByte() }.toByteArray())
            firstElement is JsonPrimitive && firstElement.intOrNull != null -> NbtIntArray(map { it.jsonPrimitive.content.toInt() }.toIntArray())
            firstElement is JsonPrimitive && firstElement.longOrNull != null -> NbtLongArray(map { it.jsonPrimitive.content.toLong() }.toLongArray())
            else -> NbtList.of(map { it.toNbtTag() })
        }
    }

    is JsonObject -> NbtCompound.of(mapValues { (_, value) -> value.toNbtTag() })
    else -> throw IllegalArgumentException("Unsupported JsonElement type")
}