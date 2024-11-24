package net.benwoodworth.knbt.tag

import kotlinx.serialization.Serializable

/**
 * NbtDouble
 *
 * @author TheFloodDragon
 * @since 2024/11/24 10:46
 */
@Serializable(NbtDoubleSerializer::class)
public class NbtDouble(public override val content: Double) : NbtTag {

    override fun equals(other: Any?): Boolean =
        this === other || (other is NbtDouble && this.content.toRawBits() == other.content.toRawBits())

    override fun hashCode(): Int = content.toRawBits().hashCode()

    override fun toString(): String = "${content}d"

    override val type: NbtType get() = NbtType.DOUBLE

}