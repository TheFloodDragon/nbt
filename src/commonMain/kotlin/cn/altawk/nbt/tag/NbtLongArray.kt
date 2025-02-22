package cn.altawk.nbt.tag

import cn.altawk.nbt.internal.Tokens
import kotlinx.serialization.Serializable

/**
 * NbtLongArray
 *
 * @author TheFloodDragon
 * @since 2025/1/5 16:43
 */
@Serializable(NbtLongArraySerializer::class)
public class NbtLongArray(
    /**
     * The content of the tag.
     */
    public override val content: LongArray
) : NbtTag {

    /**
     * Get the data at the specified index.
     */
    public operator fun get(index: Int): Long = content[index]

    /**
     * The size of the tag.
     */
    public val size: Int get() = content.size

    /**
     * Clone the tag.
     */
    public override fun clone(): NbtLongArray = NbtLongArray(content.copyOf())

    /**
     * Get the string representation of the tag.
     */
    override fun toString(): String = content.joinToString(
        separator = Tokens.VALUE_SEPARATOR,
        prefix = Tokens.ARRAY_BEGIN + Tokens.TYPE_LONG_ARRAY + Tokens.ARRAY_SIGNATURE_SEPARATOR,
        postfix = Tokens.ARRAY_END
    ) { it.toString() + Tokens.TYPE_LONG_ARRAY }

    /**
     * Check if the tag equals another.
     */
    override fun equals(other: Any?): Boolean =
        this === other || (other is NbtLongArray && this.content.contentEquals(other.content))

    /**
     * Get the hash code of the tag.
     */
    override fun hashCode(): Int = content.hashCode()

    /**
     * The type of the tag.
     */
    public override val type: NbtType get() = NbtType.LONG_ARRAY

}