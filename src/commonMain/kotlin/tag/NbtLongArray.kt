package net.benwoodworth.knbt.tag

import kotlinx.serialization.Serializable

/**
 * NbtLongArray
 *
 * @author TheFloodDragon
 * @since 2024/11/24 10:59
 */
@Serializable(NbtLongArraySerializer::class)
public class NbtLongArray(public override val content: LongArray) : NbtTag {

    public val size: Int get() = content.size

    public operator fun get(index: Int): Long = content[index]

    override fun equals(other: Any?): Boolean =
        this === other || (other is NbtLongArray && this.content.contentEquals(other.content))

    override fun hashCode(): Int = content.hashCode()

    override fun toString(): String = content.joinToString(separator = ",", prefix = "[L;", postfix = "]") { "${it}L" }

    override val type: NbtType get() = NbtType.LONG_ARRAY

}