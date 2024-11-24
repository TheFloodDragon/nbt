package net.benwoodworth.knbt.tag

import kotlinx.serialization.Serializable

/**
 * NbtByteArray
 *
 * @author TheFloodDragon
 * @since 2024/11/24 10:47
 */
@Serializable(NbtByteArraySerializer::class)
public class NbtByteArray(public override val content: ByteArray) : NbtTag {

    public val size: Int get() = content.size

    public operator fun get(index: Int): Byte = content[index]

    override fun equals(other: Any?): Boolean =
        this === other || (other is NbtByteArray && this.content.contentEquals(other.content))

    override fun hashCode(): Int = content.hashCode()

    override fun toString(): String = content.joinToString(separator = ",", prefix = "[B;", postfix = "]") { "${it}B" }

    override val type: NbtType get() = NbtType.BYTE_ARRAY

}