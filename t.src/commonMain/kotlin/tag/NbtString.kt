package net.benwoodworth.knbt.tag

import kotlinx.serialization.Serializable
import net.benwoodworth.knbt.internal.toNbtString

/**
 * NbtString
 *
 * @author TheFloodDragon
 * @since 2024/11/24 10:49
 */
@Serializable(NbtStringSerializer::class)
public class NbtString(public override val content: String) : NbtTag {

    override fun equals(other: Any?): Boolean = this === other || (other is NbtString && this.content == other.content)

    override fun hashCode(): Int = content.hashCode()

    override fun toString(): String = content.toNbtString(true)

    override val type: NbtType get() = NbtType.STRING

}