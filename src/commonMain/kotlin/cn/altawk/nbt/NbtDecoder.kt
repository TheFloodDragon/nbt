package cn.altawk.nbt

import cn.altawk.nbt.tag.NbtTag
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder

/**
 * NbtDecoder
 *
 * @author TheFloodDragon
 * @since 2025/1/25 11:36
 */
public interface NbtDecoder : Decoder, CompositeDecoder {

    /**
     * An instance of the current [NbtFormat].
     */
    public val nbt: NbtFormat

    /**
     * Decode a [NbtTag].
     */
    public fun decodeNbtTag(): NbtTag

}