package net.benwoodworth.knbt.tag

import kotlin.reflect.KClass

/**
 * NbtType
 *
 * @author TheFloodDragon
 * @since 2024/11/24 10:35
 */
public enum class NbtType(internal val id: Byte) {
    END(0),
    BYTE(1),
    SHORT(2),
    INT(3),
    LONG(4),
    FLOAT(5),
    DOUBLE(6),
    BYTE_ARRAY(7),
    STRING(8),
    LIST(9),
    COMPOUND(10),
    INT_ARRAY(11),
    LONG_ARRAY(12);

    public companion object {

        public fun from(id: Byte): NbtType? = entries.find { it.id == id }

        public fun from(clazz: KClass<out NbtTag>): NbtType =
            when (clazz.simpleName) { // String cases so it's optimized to a jump table
                "NbtByte" -> BYTE
                "NbtShort" -> SHORT
                "NbtInt" -> INT
                "NbtLong" -> LONG
                "NbtFloat" -> FLOAT
                "NbtDouble" -> DOUBLE
                "NbtByteArray" -> BYTE_ARRAY
                "NbtString" -> STRING
                "NbtList" -> LIST
                "NbtCompound" -> COMPOUND
                "NbtIntArray" -> INT_ARRAY
                "NbtLongArray" -> LONG_ARRAY
                else -> END// "Nothing", or "Void" on JVM
            }


    }

}