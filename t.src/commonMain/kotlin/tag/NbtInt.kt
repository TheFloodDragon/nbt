package net.benwoodworth.knbt.tag

import kotlinx.serialization.Serializable

/**
 * NbtInt
 *
 * @author TheFloodDragon
 * @since 2024/11/24 10:43
 */
@Serializable(NbtIntSerializer::class)
public class NbtInt(public override val content: Int) : NbtTag {

    override fun equals(other: Any?): Boolean = this === other || (other is NbtInt && this.content == other.content)

    override fun hashCode(): Int = content.hashCode()

    override fun toString(): String = content.toString()

    override val type: NbtType get() = NbtType.INT

}