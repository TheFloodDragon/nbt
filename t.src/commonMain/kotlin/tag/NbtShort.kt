package net.benwoodworth.knbt.tag

import kotlinx.serialization.Serializable

/**
 * NbtShort
 *
 * @author TheFloodDragon
 * @since 2024/11/24 10:42
 */
@Serializable(NbtShortSerializer::class)
public class NbtShort(public override val content: Short) : NbtTag {

    override fun equals(other: Any?): Boolean = this === other || (other is NbtShort && this.content == other.content)

    override fun hashCode(): Int = content.hashCode()

    override fun toString(): String = "${content}s"

    override val type: NbtType get() = NbtType.SHORT

}