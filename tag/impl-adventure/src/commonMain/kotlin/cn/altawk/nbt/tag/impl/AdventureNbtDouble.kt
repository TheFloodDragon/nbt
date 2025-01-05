package cn.altawk.nbt.tag.impl

import cn.altawk.nbt.tag.NbtDouble
import net.kyori.adventure.nbt.ByteBinaryTag
import net.kyori.adventure.nbt.DoubleBinaryTag

/**
 * AdventureNbtDouble
 *
 * @author TheFloodDragon
 * @since 2025/1/5 19:38
 */
public class AdventureNbtDouble(
    public override val source: DoubleBinaryTag
) : NbtDouble, AdventureNbtTag() {

    public constructor(value: Double) : this(DoubleBinaryTag.doubleBinaryTag(value))

    override val content: Double get() = source.value()

    override fun clone(): AdventureNbtDouble = AdventureNbtDouble(source.value())

}