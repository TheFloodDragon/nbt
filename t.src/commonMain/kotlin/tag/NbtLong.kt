package net.benwoodworth.knbt.tag

import kotlinx.serialization.Serializable

/**
 * NbtLong
 *
 * @author TheFloodDragon
 * @since 2024/11/24 10:44
 */
@Serializable(NbtLongSerializer::class)
public class NbtLong(public override val content: Long) : NbtTag {

    override fun equals(other: Any?): Boolean = this === other || (other is NbtLong && this.content == other.content)

    override fun hashCode(): Int = content.hashCode()

    override fun toString(): String = "${content}L"


    override val type: NbtType get() = NbtType.LONG

}