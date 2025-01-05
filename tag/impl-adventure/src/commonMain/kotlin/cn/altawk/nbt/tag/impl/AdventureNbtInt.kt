package cn.altawk.nbt.tag.impl

import cn.altawk.nbt.tag.NbtInt
import net.kyori.adventure.nbt.IntBinaryTag

/**
 * AdventureNbtInt
 *
 * @author TheFloodDragon
 * @since 2025/1/5 19:35
 */
public class AdventureNbtInt(
    public override val source: IntBinaryTag
) : NbtInt, AdventureNbtTag() {

    public constructor(value: Int) : this(IntBinaryTag.intBinaryTag(value))

    override val content: Int get() = source.value()

    override fun clone(): AdventureNbtInt = AdventureNbtInt(source.value())

}