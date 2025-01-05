package cn.altawk.nbt.tag.impl

import cn.altawk.nbt.tag.NbtTag
import cn.altawk.nbt.tag.NbtType
import net.kyori.adventure.nbt.BinaryTag
import net.kyori.adventure.nbt.EndBinaryTag

/**
 * AdventureNbtTag
 *
 * @author TheFloodDragon
 * @since 2025/1/5 19:45
 */
public abstract class AdventureNbtTag : NbtTag {

    public abstract val source: BinaryTag

    override fun hashCode(): Int = source.hashCode()

    override fun equals(other: Any?): Boolean = source == other

    override fun toString(): String = source.toString()

    public object End : AdventureNbtTag() {
        override val source: BinaryTag = EndBinaryTag.endBinaryTag()
        override val type: NbtType get() = NbtType.END
        override val content: Unit get() = Unit
        override fun clone(): End = this
    }

}