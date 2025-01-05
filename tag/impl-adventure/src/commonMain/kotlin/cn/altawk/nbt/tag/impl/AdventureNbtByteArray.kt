package cn.altawk.nbt.tag.impl

import cn.altawk.nbt.tag.NbtByteArray
import net.kyori.adventure.nbt.ByteArrayBinaryTag

/**
 * AdventureNbtByteArray
 *
 * @author TheFloodDragon
 * @since 2025/1/5 19:44
 */
public class AdventureNbtByteArray(
    public override val source: ByteArrayBinaryTag
) : NbtByteArray, AdventureNbtTag() {

    public constructor(vararg value: Byte) : this(ByteArrayBinaryTag.byteArrayBinaryTag(*value))

    override val content: ByteArray get() = source.value()

    override fun clone(): AdventureNbtByteArray = AdventureNbtByteArray(*source.value())

    override fun get(index: Int): Byte = source.get(index)

    override val size: Int get() = source.size()

}