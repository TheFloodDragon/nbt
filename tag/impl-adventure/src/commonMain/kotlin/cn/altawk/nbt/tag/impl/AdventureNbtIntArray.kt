package cn.altawk.nbt.tag.impl

import cn.altawk.nbt.tag.NbtIntArray
import net.kyori.adventure.nbt.IntArrayBinaryTag

/**
 * AdventureNbtIntArray
 *
 * @author TheFloodDragon
 * @since 2025/1/5 19:43
 */
public class AdventureNbtIntArray(
    public override val source: IntArrayBinaryTag
) : NbtIntArray, AdventureNbtTag() {

    public constructor(vararg value: Int) : this(IntArrayBinaryTag.intArrayBinaryTag(*value))

    override val content: IntArray get() = source.value()

    override fun clone(): AdventureNbtIntArray = AdventureNbtIntArray(*source.value())

    override fun get(index: Int): Int = source.get(index)

    override val size: Int get() = source.size()

}