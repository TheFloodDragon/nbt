package net.benwoodworth.knbt.tag

import kotlinx.serialization.Serializable
import net.benwoodworth.knbt.internal.appendNbtString

/**
 * NbtCompound
 *
 * @author TheFloodDragon
 * @since 2024/11/24 10:54
 */
@Serializable(NbtCompoundSerializer::class)
public class NbtCompound(public override val content: MutableMap<String, NbtTag>) : NbtTag,
    MutableMap<String, NbtTag> by content {

    public constructor() : this(LinkedHashMap())

    override fun equals(other: Any?): Boolean = this === other || (other is NbtCompound && content == other.content)

    override fun hashCode(): Int = content.hashCode()

    override fun toString(): String =
        content.entries.joinToString(separator = ",", prefix = "{", postfix = "}") { (name, value) ->
            buildString {
                appendNbtString(name)
                append(':')
                append(value)
            }
        }

    override val type: NbtType get() = NbtType.COMPOUND

    public companion object {

        public fun of(map: Map<String, NbtTag>): NbtCompound = NbtCompound(map.toMutableMap())

    }

}