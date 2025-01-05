package cn.altawk.nbt.tag.impl

import cn.altawk.nbt.tag.NbtLong
import net.kyori.adventure.nbt.LongBinaryTag

/**
 * AdventureNbtLong
 *
 * @author TheFloodDragon
 * @since 2025/1/5 19:35
 */
public class AdventureNbtLong(
    public override val source: LongBinaryTag
) : NbtLong, AdventureNbtTag() {

    public constructor(value: Long) : this(LongBinaryTag.longBinaryTag(value))

    override val content: Long get() = source.value()

    override fun clone(): AdventureNbtLong = AdventureNbtLong(source.value())

}