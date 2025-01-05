package cn.altawk.nbt.tag.impl

import cn.altawk.nbt.tag.NbtLongArray
import net.kyori.adventure.nbt.LongArrayBinaryTag

/**
 * AdventureNbtLongArray
 *
 * @author TheFloodDragon
 * @since 2025/1/5 19:40
 */
public class AdventureNbtLongArray(
    public override val source: LongArrayBinaryTag
) : NbtLongArray, AdventureNbtTag() {

    public constructor(vararg value: Long) : this(LongArrayBinaryTag.longArrayBinaryTag(*value))

    override val content: LongArray get() = source.value()

    override fun clone(): AdventureNbtLongArray = AdventureNbtLongArray(*source.value())

    override fun get(index: Int): Long = source.get(index)

    override val size: Int get() = source.size()

}