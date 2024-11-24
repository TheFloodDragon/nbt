package net.benwoodworth.knbt.tag

import kotlinx.serialization.Serializable

/**
 * NbtIntArray
 *
 * @author TheFloodDragon
 * @since 2024/11/24 10:58
 */
@Serializable(NbtIntArraySerializer::class)
public class NbtIntArray(public override val content: IntArray) : NbtTag {

    public val size: Int get() = content.size

    public operator fun get(index: Int): Int = content[index]

    override fun equals(other: Any?): Boolean =
        this === other || (other is NbtIntArray && this.content.contentEquals(other.content))

    override fun hashCode(): Int = content.hashCode()

    override fun toString(): String = content.joinToString(separator = ",", prefix = "[I;", postfix = "]")

    override val type: NbtType get() = NbtType.INT_ARRAY

}