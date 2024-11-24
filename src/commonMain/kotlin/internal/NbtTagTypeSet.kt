package net.benwoodworth.knbt.internal

import net.benwoodworth.knbt.tag.NbtType

internal class NbtTypeSet(
    elements: Collection<NbtType>
) {

    /**
     * Bits indicating whether each [NbtType] in this set, with `1` indicating that it is contained, and
     * [NbtType.bit] assigning bit positions.
     */
    private val elementBits: Int =
        elements.fold(0) { bits, tagType -> bits or tagType.bit }

    private val NbtType.bit: Int
        get() = 1 shl id.toInt()

    operator fun contains(type: NbtType): Boolean =
        (elementBits and type.bit) != 0

    override fun equals(other: Any?): Boolean =
        this === other || (other is NbtTypeSet && elementBits == other.elementBits)

    override fun hashCode(): Int = elementBits

    override fun toString(): String =
        NbtType.entries
            .filter { it in this }
            .joinToString(prefix = "[", postfix = "]")

}
