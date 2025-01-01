package net.benwoodworth.knbt.test.parameters

import com.benwoodworth.parameterize.ParameterizeScope
import com.benwoodworth.parameterize.parameter
import com.benwoodworth.parameterize.parameterize
import net.benwoodworth.knbt.tag.*

private fun NbtTag.repeatInNbtList(size: Int): NbtList<*> = when (this) {
    is NbtByte -> NbtList(MutableList(size) { this })
    is NbtShort -> NbtList(MutableList(size) { this })
    is NbtInt -> NbtList(MutableList(size) { this })
    is NbtLong -> NbtList(MutableList(size) { this })
    is NbtFloat -> NbtList(MutableList(size) { this })
    is NbtDouble -> NbtList(MutableList(size) { this })
    is NbtByteArray -> NbtList(MutableList(size) { this })
    is NbtString -> NbtList(MutableList(size) { this })
    is NbtList<*> -> NbtList(MutableList(size) { this })
    is NbtCompound -> NbtList(MutableList(size) { this })
    is NbtIntArray -> NbtList(MutableList(size) { this })
    is NbtLongArray -> NbtList(MutableList(size) { this })
}

/**
 * [NbtList]s of every element type with sizes 0..2
 */
fun ParameterizeScope.parameterOfNbtListContentEdgeCases() = parameter {
    sequence {
        // TAG_End
        yield(NbtList<NbtTag>())

        parameterize {
            val entry by parameterOfNbtTagTypeEdgeCases()
            val size by parameter(0..2)

            yield(entry.repeatInNbtList(size))
        }
    }
}
