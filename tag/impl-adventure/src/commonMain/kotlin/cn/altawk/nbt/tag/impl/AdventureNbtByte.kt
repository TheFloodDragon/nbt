package cn.altawk.nbt.tag.impl

import cn.altawk.nbt.tag.NbtByte
import net.kyori.adventure.nbt.ByteBinaryTag

/**
 * AdventureNbtByte
 *
 * @author TheFloodDragon
 * @since 2025/1/5 19:35
 */
public class AdventureNbtByte(
    public override val source: ByteBinaryTag
) : NbtByte, AdventureNbtTag() {

    public constructor(value: Byte) : this(ByteBinaryTag.byteBinaryTag(value))

    override val content: Byte get() = source.value()

    override fun clone(): AdventureNbtByte = AdventureNbtByte(source.value())

}