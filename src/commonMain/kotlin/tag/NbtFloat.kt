package net.benwoodworth.knbt.tag

import kotlinx.serialization.Serializable

/**
 * NbtFloat
 *
 * @author TheFloodDragon
 * @since 2024/11/24 10:45
 */
@Serializable(NbtFloatSerializer::class)
public class NbtFloat(public override val content: Float) : NbtTag {

    override fun equals(other: Any?): Boolean =
        this === other || (other is NbtFloat && this.content.toRawBits() == other.content.toRawBits())

    override fun hashCode(): Int = content.toRawBits().hashCode()

    override fun toString(): String = "${content}f"

    override val type: NbtType get() = NbtType.FLOAT

}