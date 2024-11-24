package net.benwoodworth.knbt.tag

import kotlinx.serialization.Serializable

/**
 * NbtByte
 *
 * @author TheFloodDragon
 * @since 2024/11/24 10:30
 */
@Serializable(NbtByteSerializer::class)
public class NbtByte(public override val content: Byte) : NbtTag {

    /**
     * Create an [NbtByte] representing a [Boolean]: `false = 0b`, `true = 1b`.
     */
    public constructor(value: Boolean) : this(if (value) 1 else 0)

    /**
     * Convert this [NbtByte] to its [Boolean] representation: `0b = false`, `1b = true`.
     *
     * In order to match Minecraft's lenient behavior, all other values convert to `true`.
     */
    public fun toBoolean(): Boolean = content != 0.toByte()

    override fun equals(other: Any?): Boolean = this === other || (other is NbtByte && this.content == other.content)

    override fun hashCode(): Int = content.hashCode()

    override fun toString(): String = "${content}b"

    override val type: NbtType get() = NbtType.BYTE

}