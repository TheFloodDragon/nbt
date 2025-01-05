package cn.altawk.nbt.tag.impl

import cn.altawk.nbt.tag.NbtShort
import net.kyori.adventure.nbt.ShortBinaryTag

/**
 * AdventureNbtShort
 *
 * @author TheFloodDragon
 * @since 2025/1/5 19:35
 */
public class AdventureNbtShort(
    public override val source: ShortBinaryTag
) : NbtShort, AdventureNbtTag() {

    public constructor(value: Short) : this(ShortBinaryTag.shortBinaryTag(value))

    override val content: Short get() = source.value()

    override fun clone(): AdventureNbtShort = AdventureNbtShort(source.value())

}