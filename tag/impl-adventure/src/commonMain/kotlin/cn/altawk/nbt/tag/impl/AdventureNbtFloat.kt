package cn.altawk.nbt.tag.impl

import cn.altawk.nbt.tag.NbtFloat
import net.kyori.adventure.nbt.ByteBinaryTag
import net.kyori.adventure.nbt.FloatBinaryTag

/**
 * AdventureNbtFloat
 *
 * @author TheFloodDragon
 * @since 2025/1/5 19:38
 */
public class AdventureNbtFloat(
    public override val source: FloatBinaryTag
) : NbtFloat, AdventureNbtTag() {

    public constructor(value: Float) : this(FloatBinaryTag.floatBinaryTag(value))

    override val content: Float get() = source.value()

    override fun clone(): AdventureNbtFloat = AdventureNbtFloat(source.value())

}