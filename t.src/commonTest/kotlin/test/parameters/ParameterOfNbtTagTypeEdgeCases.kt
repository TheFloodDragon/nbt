package net.benwoodworth.knbt.test.parameters

import com.benwoodworth.parameterize.ParameterizeScope
import com.benwoodworth.parameterize.parameter
import net.benwoodworth.knbt.tag.*

/**
 * An [NbtTag] of each type.
 */
fun ParameterizeScope.parameterOfNbtTagTypeEdgeCases() = parameter {
    NbtType.entries.asSequence().mapNotNull { type ->
        when (type) {
            NbtType.END -> null
            NbtType.BYTE -> NbtByte(0)
            NbtType.SHORT -> NbtShort(0)
            NbtType.INT -> NbtInt(0)
            NbtType.LONG -> NbtLong(0L)
            NbtType.FLOAT -> NbtFloat(0.0f)
            NbtType.DOUBLE -> NbtDouble(0.0)
            NbtType.BYTE_ARRAY -> NbtByteArray(byteArrayOf())
            NbtType.STRING -> NbtString("")
            NbtType.LIST -> NbtList<NbtTag>()
            NbtType.COMPOUND -> NbtCompound()
            NbtType.INT_ARRAY -> NbtIntArray(intArrayOf())
            NbtType.LONG_ARRAY -> NbtLongArray(longArrayOf())
        }
    }
}
