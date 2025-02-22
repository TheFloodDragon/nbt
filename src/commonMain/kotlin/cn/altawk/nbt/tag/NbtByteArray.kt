package cn.altawk.nbt.tag

import cn.altawk.nbt.internal.Tokens
import kotlinx.serialization.Serializable

/**
 * NbtByteArray
 *
 * @author TheFloodDragon
 * @since 2025/1/5 16:43
 */
@Serializable(NbtByteArraySerializer::class)
public class NbtByteArray(
    /**
     * The content of the tag.
     */
    public override val content: ByteArray
) : NbtTag {

    /**
     * Get the data at the specified index.
     */
    public operator fun get(index: Int): Byte = content[index]

    /**
     * The size of the tag.
     */
    public val size: Int get() = content.size

    /**
     * Clone the tag.
     */
    public override fun clone(): NbtByteArray = NbtByteArray(content.clone())

    /**
     * Get the string representation of the tag.
     */
    override fun toString(): String = content.joinToString(
        separator = Tokens.VALUE_SEPARATOR,
        prefix = Tokens.ARRAY_BEGIN + Tokens.TYPE_BYTE_ARRAY + Tokens.ARRAY_SIGNATURE_SEPARATOR,
        postfix = Tokens.ARRAY_END
    ) { it.toString() + Tokens.TYPE_BYTE_ARRAY }

    /**
     * Check if the tag is equal to another object.
     */
    override fun equals(other: Any?): Boolean =
        this === other || (other is NbtByteArray && this.content.contentEquals(other.content))

    override fun hashCode(): Int = content.hashCode()

    /**
     * The type of the tag.
     */
    public override val type: NbtType get() = NbtType.BYTE_ARRAY

}