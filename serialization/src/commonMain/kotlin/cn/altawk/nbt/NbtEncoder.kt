package cn.altawk.nbt

import cn.altawk.nbt.tag.NbtTag
import kotlinx.serialization.encoding.CompositeEncoder
import kotlinx.serialization.encoding.Encoder

/**
 * NbtEncoder
 *
 * @author TheFloodDragon
 * @since 2025/1/25 11:15
 */
public interface NbtEncoder : Encoder, CompositeEncoder {

    /**
     * An instance of the current [NbtFormat].
     */
    public val nbt: NbtFormat

    /**
     * TODO Document
     */
    public fun encodeNbtTag(tag: NbtTag)

}