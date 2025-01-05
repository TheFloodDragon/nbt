package cn.altawk.nbt.tag.impl

import cn.altawk.nbt.tag.NbtString
import net.kyori.adventure.nbt.StringBinaryTag

/**
 * AdventureNbtString
 *
 * @author TheFloodDragon
 * @since 2025/1/5 19:22
 */
public class AdventureNbtString(
    public override val source: StringBinaryTag
) : NbtString, AdventureNbtTag() {

    public constructor(value: String) : this(StringBinaryTag.stringBinaryTag(value))

    override val content: String get() = source.value()

    override fun clone(): AdventureNbtString = AdventureNbtString(source.value())

}